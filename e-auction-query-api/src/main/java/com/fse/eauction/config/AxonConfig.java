package com.fse.eauction.config;

import org.apache.logging.log4j.util.Strings;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.rabbitmq.client.Channel;


@Configuration
public class AxonConfig {
	
	@Value("${spring.data.mongodb.uri:}")
	String mongoUri;
	
	@Value("${spring.data.mongodb.host:}")
	String mongoHost;
	
	@Value("${spring.data.mongodb.port:}")
	String mongoPort;
		      
	@Bean
	public MongoClient mongoClient(){
		 MongoClient mongoClient = !Strings.isBlank(mongoUri) ? MongoClients.create(mongoUri) : MongoClients.create("mongodb://" + mongoHost + ":" + mongoPort);
		 return mongoClient;
	}
	
	@Bean
	public EventStorageEngine storageEngine(MongoClient client) {
	    return MongoEventStorageEngine.builder().mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build();
	}

	@Bean
	public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
	    return EmbeddedEventStore.builder()
	            .storageEngine(storageEngine)
	            .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
	            .build();
	}
	
	@Bean
	public Queue queue() {
	   return QueueBuilder.durable("e-auction").build();
	}

	@Bean
	public Exchange exchange() {
	   return ExchangeBuilder.fanoutExchange("amq.fanout").durable(true).build();
	}

	@Bean
	public Binding binding() {
	   return BindingBuilder.bind(queue()).to(exchange()).with("e-auction.routing.key").noargs();
	}

	@Autowired
	public void configure(AmqpAdmin admin) {
	   admin.declareQueue(queue());
	   admin.declareExchange(exchange());
	   admin.declareBinding(binding());
	}
	
	@Bean
	public SpringAMQPMessageSource myQueueMessageSource(Serializer serializer) {
	    return new SpringAMQPMessageSource(DefaultAMQPMessageConverter.builder().serializer(serializer).build()) {

	        @RabbitListener(queues = "e-auction")
	        @Override
	        public void onMessage(Message message, Channel channel) {
	            super.onMessage(message, channel);
	        }
	    };
	}
}