package com.fse.eauction.service;

import org.springframework.stereotype.Service;

import com.fse.eauction.model.PlaceBidRequest;
import com.fse.eauction.model.PlaceBidResponse;

@Service
public interface BidService {
	PlaceBidResponse place(PlaceBidRequest placeBidRequest);
	void updateBidAmount(String productId, String buyerEmailId, Double newBidAmount);
}
