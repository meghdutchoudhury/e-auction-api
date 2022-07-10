package com.fse.eauction.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fse.eauction.controller.BidController;
import com.fse.eauction.model.GetBidsResponse;
import com.fse.eauction.query.FindAllBidsOnProductQuery;
import com.fse.eauction.service.BidService;

@Service
public class BidQueryHandler {

	Logger logger = LoggerFactory.getLogger(BidController.class);

    @QueryHandler
    public GetBidsResponse handle(FindAllBidsOnProductQuery query, BidService bidService){
    	logger.info("Handling FindAllBidsOnProductQuery");
        return bidService.findAllBidsOnProduct(query.getProductId(), query.getFilterBy(), query.getSortKey(), query.getSortDirection(), query.getPageNumber(), query.getPageSize());
    }
}