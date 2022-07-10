package com.fse.eauction.model;

public class GetBidsResponseBidEntity {

	private String id;
	private Double amount;
	private GetBidsResponseBuyerEntity buyer;

	public GetBidsResponseBidEntity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Bid [id=" + id  + ", amount=" + amount + ", Buyer=" + buyer + "]";
	}

	public GetBidsResponseBuyerEntity getBuyer() {
		return buyer;
	}

	public void setBuyer(GetBidsResponseBuyerEntity buyer) {
		this.buyer = buyer;
	}

	public static class Builder {

		private String id;
		private Double amount;
		private GetBidsResponseBuyerEntity buyer;

		public Builder() {    
		}

		Builder(String id, Double amount, GetBidsResponseBuyerEntity buyer) {    
			this.id = id; 
			this.amount = amount; 
			this.buyer = buyer;             
		}

		public Builder id(String id){
			this.id = id;
			return Builder.this;
		}

		public Builder amount(Double amount){
			this.amount = amount;
			return Builder.this;
		}

		public Builder buyer(GetBidsResponseBuyerEntity buyer){
			this.buyer = buyer;
			return Builder.this;
		}

		public GetBidsResponseBidEntity build() {

			return new GetBidsResponseBidEntity(this);
		}
	}

	private GetBidsResponseBidEntity(Builder builder) {
		this.id = builder.id; 
		this.amount = builder.amount; 
		this.buyer = builder.buyer;     
	}

}
