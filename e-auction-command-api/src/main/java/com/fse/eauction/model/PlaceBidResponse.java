package com.fse.eauction.model;

public class PlaceBidResponse {

	private String bidId;

	public PlaceBidResponse() {
		super();
	}

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	@Override
	public String toString() {
		return "PlaceBidResponse [bidId=" + bidId + "]";
	}

}
