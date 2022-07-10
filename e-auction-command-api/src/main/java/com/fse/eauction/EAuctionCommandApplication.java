package com.fse.eauction;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableRabbit
public class EAuctionCommandApplication {
	public static void main(String[] args) {
		SpringApplication.run(EAuctionCommandApplication.class, args);
	}
	
}
