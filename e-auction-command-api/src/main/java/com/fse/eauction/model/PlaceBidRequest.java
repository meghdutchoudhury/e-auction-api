package com.fse.eauction.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class PlaceBidRequest {

	@NotNull
	@Schema(description = "Product ID to bid for", required = true)
	private String productId;
	
	@NotNull
	@Min(0)
	@Schema(description = "Bid amount", required = true)
	private Double amount;
	
	@NotNull
	@Valid
	PlaceBidRequestBuyerEntity placeBidRequestBuyerEntity;

	public PlaceBidRequest() {
		super();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
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
		return "PlaceBidRequest [productId=" + productId + ", amount=" + amount + ", placeBidRequestBuyerEntity="
				+ placeBidRequestBuyerEntity + "]";
	}

	public PlaceBidRequestBuyerEntity getPlaceBidRequestBuyerEntity() {
		return placeBidRequestBuyerEntity;
	}

	public void setPlaceBidRequestBuyerEntity(PlaceBidRequestBuyerEntity placeBidRequestBuyerEntity) {
		this.placeBidRequestBuyerEntity = placeBidRequestBuyerEntity;
	}

	
}
