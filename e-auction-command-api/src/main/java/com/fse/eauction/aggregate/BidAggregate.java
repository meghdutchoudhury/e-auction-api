package com.fse.eauction.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fse.eauction.command.PlaceBidCommand;
import com.fse.eauction.event.PlaceBidEvent;
import com.fse.eauction.model.PlaceBidResponse;
import com.fse.eauction.service.BidService;

@Aggregate
public class BidAggregate {

	Logger log = LoggerFactory.getLogger(BidAggregate.class);
	
	@AggregateIdentifier
	private String bidId;
	
	public BidAggregate() {
	}
	
	@CommandHandler
	public BidAggregate(PlaceBidCommand postBidCommand) {
		log.info("PlaceBidCommand received");
		AggregateLifecycle.apply(new PlaceBidEvent(
				postBidCommand.getId(),
				postBidCommand.getPlaceBidRequest()));
	}
	
	@EventSourcingHandler
	public void on(PlaceBidEvent postBidEvent, BidService bidService) {
		log.info("Entered PlaceBidEvent with event object {}", postBidEvent);
		
		PlaceBidResponse placeBidResponse = bidService.place(postBidEvent.getPlaceBidRequest());
		this.bidId = placeBidResponse.getBidId();
		
		log.info("Applied PlaceBidEvent");
	}
}