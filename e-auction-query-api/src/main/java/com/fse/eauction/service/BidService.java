package com.fse.eauction.service;

import org.springframework.stereotype.Service;

import com.fse.eauction.model.BidSortKey;
import com.fse.eauction.model.GetBidsResponse;
import com.fse.eauction.model.SortDirection;

@Service
public interface BidService {
	GetBidsResponse findAllBidsOnProduct(String productId, String filterBy, BidSortKey sortKey, SortDirection sortDirection, Integer pageNumber, Integer pageSize);
}
