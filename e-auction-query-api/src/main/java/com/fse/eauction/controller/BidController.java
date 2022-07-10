package com.fse.eauction.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fse.eauction.model.BidSortKey;
import com.fse.eauction.model.GetBidsResponse;
import com.fse.eauction.model.SortDirection;
import com.fse.eauction.query.FindAllBidsOnProductQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Validated
public class BidController {

	Logger logger = LoggerFactory.getLogger(BidController.class);

	@Autowired
	QueryGateway queryGateway;

	@RequestMapping(path = "/seller/show-bids/{productId}", method = RequestMethod.GET)
	@Operation(summary = "findAllBidsOnProduct", description = "Fetch bids for product ID")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product found with bids", content = @Content(schema = @Schema(implementation = GetBidsResponse.class))), 
	        @ApiResponse(responseCode = "404", description = "Product does not exist"), 
	})
	public ResponseEntity<GetBidsResponse> findAllBidsOnProduct(
			@PathVariable String productId,
			@RequestParam(required = false, defaultValue = "") String filterBy,
			@RequestParam(required = false, defaultValue = "amount") BidSortKey sortKey,
			@RequestParam(required = false, defaultValue = "desc") SortDirection sortDirection,
			@RequestParam(required = false, defaultValue = "1") @Valid @Min(1) Integer pageNumber,
			@RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") @Valid @Min(1) Integer pageSize
			) {
		return new ResponseEntity<GetBidsResponse>(queryGateway.query(new FindAllBidsOnProductQuery(productId, filterBy.toLowerCase(), sortKey, sortDirection, pageSize, pageNumber), GetBidsResponse.class).join(), HttpStatus.OK);
	}

}
