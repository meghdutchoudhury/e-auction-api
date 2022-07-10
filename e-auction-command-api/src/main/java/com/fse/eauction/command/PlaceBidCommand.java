package com.fse.eauction.command;

import com.fse.eauction.model.PlaceBidRequest;

public class PlaceBidCommand extends BaseCommand<String> {

    private final PlaceBidRequest placeBidRequest;

    public PlaceBidCommand(String id, PlaceBidRequest placeBidRequest) {
        super(id);
        this.placeBidRequest = placeBidRequest;
    }

    public PlaceBidRequest getPlaceBidRequest() {
        return placeBidRequest;
    }
}