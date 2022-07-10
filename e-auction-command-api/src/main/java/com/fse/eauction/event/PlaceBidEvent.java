package com.fse.eauction.event;

import com.fse.eauction.model.PlaceBidRequest;

public class PlaceBidEvent extends BaseEvent<String> {

	private final PlaceBidRequest placeBidRequest;

    public PlaceBidEvent(String id, PlaceBidRequest placeBidRequest) {
        super(id);
        this.placeBidRequest = placeBidRequest;
    }

    public PlaceBidRequest getPlaceBidRequest() {
        return placeBidRequest;
    }

	@Override
	public String toString() {
		return "PostBidEvent [placeBidRequest=" + placeBidRequest + "]";
	}
}