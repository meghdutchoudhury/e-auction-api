package com.fse.eauction.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("bids")
public class Bid {

	@Id
	private String id;

	private String productId;

	private Double amount;

	private Buyer buyer;

	public Bid() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct() {
		return productId;
	}

	public void setProduct(String productId) {
		this.productId = productId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Bid [id=" + id + ", productId=" + productId + ", amount=" + amount + ", Buyer=" + buyer + "]";
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
	public static class Builder {
	    
	    private String id;
	    private String productId;
	    private Double amount;
	    private Buyer buyer;

	    public Builder() {    
	    }
	      
	    Builder(String id, String productId, Double amount, Buyer Buyer) {    
	      this.id = id; 
	      this.productId = productId; 
	      this.amount = amount; 
	      this.buyer = Buyer;             
	    }
	        
	    public Builder id(String id){
	      this.id = id;
	      return Builder.this;
	    }

	    public Builder productId(String productId){
	      this.productId = productId;
	      return Builder.this;
	    }

	    public Builder amount(Double amount){
	      this.amount = amount;
	      return Builder.this;
	    }

	    public Builder buyer(Buyer buyer){
	      this.buyer = buyer;
	      return Builder.this;
	    }

	    public Bid build() {

	        return new Bid(this);
	    }
	  }

	  private Bid(Builder builder) {
	    this.id = builder.id; 
	    this.productId = builder.productId; 
	    this.amount = builder.amount; 
	    this.buyer = builder.buyer;     
	  }

}
