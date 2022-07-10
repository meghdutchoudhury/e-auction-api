package com.fse.eauction.query;

import com.fse.eauction.model.BidSortKey;
import com.fse.eauction.model.SortDirection;

public class FindAllBidsOnProductQuery {
	
	String productId;
	String filterBy;
	SortDirection sortDirection;
	BidSortKey sortKey;
	Integer pageSize;
	Integer pageNumber;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public FindAllBidsOnProductQuery(String productId) {
		super();
		this.productId = productId;
	}

	public FindAllBidsOnProductQuery(String productId, String filterBy, BidSortKey sortKey, SortDirection sortDirection,
			Integer pageSize, Integer pageNumber) {
		super();
		this.productId = productId;
		this.filterBy = filterBy;
		this.sortDirection = sortDirection;
		this.sortKey = sortKey;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}

	public SortDirection getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}

	public BidSortKey getSortKey() {
		return sortKey;
	}

	public void setSortKey(BidSortKey sortKey) {
		this.sortKey = sortKey;
	}
}
