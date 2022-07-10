package com.fse.eauction.model;

import java.util.ArrayList;
import java.util.List;

public class GetBidsResponse {

	public GetBidsResponse() {
		super();
	}

	private GetBidsResponseProductEntity product;
	private List<GetBidsResponseBidEntity> bids;

	public GetBidsResponseProductEntity getProduct() {
		return product;
	}
	public void setProduct(GetBidsResponseProductEntity product) {
		this.product = product;
	}
	public List<GetBidsResponseBidEntity> getBids() {
		return bids;
	}
	public void setBids(List<GetBidsResponseBidEntity> bids) {
		this.bids = bids;
	}
	@Override
	public String toString() {
		return "GetBidsResponse [product=" + product + ", bids=" + bids + "]";
	}

	public static class Builder {

		private GetBidsResponseProductEntity product;
		private List<GetBidsResponseBidEntity> bids = new ArrayList<GetBidsResponseBidEntity>();

		public Builder() {    
		}

		Builder(GetBidsResponseProductEntity product, List<GetBidsResponseBidEntity> bids) {    
			this.product = product; 
			this.bids = bids;             
		}

		public Builder product(GetBidsResponseProductEntity product){
			this.product = product;
			return Builder.this;
		}

		public Builder bids(List<GetBidsResponseBidEntity> bids){
			this.bids = bids;
			return Builder.this;
		}

		public Builder addBids(GetBidsResponseBidEntity bids){
			this.bids.add(bids);
			return Builder.this;
		}

		public GetBidsResponse build() {

			return new GetBidsResponse(this);
		}
	}

	private GetBidsResponse(Builder builder) {
		this.product = builder.product; 
		this.bids = builder.bids;     
	}

}
