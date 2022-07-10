package com.fse.eauction.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fse.eauction.model.Product.Category;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateProductRequest {
	
	@Schema(description = "ID of the product", example = "629f8b3dbb790d4151350eb3", required = false)
	private String id;

	@NotNull
    @Size(min=5, max=30)
	@Schema(description = "Name of the product", example = "My Product", required = true)
	private String name;
	
	@Schema(description = "Short Description of the product", example = "My Product Short Description", required = false)
	private String shortDescription;
	
	@Schema(description = "Detailed Description of the product", example = "My Product Detailed Description", required = false)
	private String detailedDescription;
	
	@Schema(description = "Category of the product", example = "Painting", required = true)
	private Category category;
	
	@NotNull
	@Min(0)
	@Schema(description = "Starting price of the product", example = "100", required = true)
	private Double startingPrice;
	
	@NotNull
	@Schema(description = "Bid end date for the product", example = "2023-06-01 00:00:00", required = true)
	private Date bidEndDate;
	
	@NotNull
	@Valid
	@Schema(description = "Seller description", required = true)
	private CreateProductRequestSellerEntity seller;

	public CreateProductRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDetailedDescription() {
		return detailedDescription;
	}

	public void setDetailedDescription(String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Date getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(Date bidEndDate) {
		this.bidEndDate = bidEndDate;
	}

	public CreateProductRequestSellerEntity getSeller() {
		return seller;
	}

	public void setSeller(CreateProductRequestSellerEntity seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription
				+ ", detailedDescription=" + detailedDescription + ", category=" + category + ", startingPrice="
				+ startingPrice + ", bidEndDate=" + bidEndDate + ", seller=" + seller + "]";
	}

}
