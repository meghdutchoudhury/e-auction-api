package com.fse.eauction.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.eauction.command.PlaceBidCommand;
import com.fse.eauction.model.PlaceBidRequest;
import com.fse.eauction.service.BidService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class BidController {

	Logger logger = LoggerFactory.getLogger(BidController.class);
	
	@Autowired
    CommandGateway commandGateway;

	@Autowired
	BidService bidService;

	@RequestMapping(path = "/buyer/place-bid", method = RequestMethod.POST)
	@Operation(summary = "placeBid", description = "Place bid on given product")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Placed bid on product", content = @Content(schema = @Schema(implementation = String.class))), 
	        @ApiResponse(responseCode = "400", description = "Invalid input"),
	})	
	public CompletableFuture<String> placeBid(@Validated @RequestBody PlaceBidRequest placeBidRequest) {
		logger.info("Entered placeBid with request {}", placeBidRequest);
		return commandGateway.send(new PlaceBidCommand(UUID.randomUUID().toString(), placeBidRequest));
	}

	@RequestMapping(path = "/buyer/update-bid/{productId}/{buyerEmailld}/{newBidAmount}", method = RequestMethod.PUT)
	@Operation(summary = "updateBid", description = "Place bid on given product")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Placed bid on product", content = @Content(schema = @Schema(implementation = Void.class))), 
	        @ApiResponse(responseCode = "400", description = "Invalid input"),
	})	
	public ResponseEntity<Void> updateBid(@PathVariable String productId, @PathVariable String buyerEmailld, @PathVariable Double newBidAmount) {
		logger.info("Entered updateBid() with productId: {}, buyerEmailld: {}, newBidAmount: {}", productId, buyerEmailld, newBidAmount);
		bidService.updateBidAmount(productId, buyerEmailld, newBidAmount);
		logger.info("Completed updateBid()");
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
