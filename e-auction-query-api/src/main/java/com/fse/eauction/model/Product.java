package com.fse.eauction.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
public class Product {

	enum Category {
		Painting, Sculptor, Ornament
	}
	
	@Id
	private String id;

    private String name;
	
	private String shortDescription;
	
	private String detailedDescription;
	
	private Category category;
	
	private Double startingPrice;
	
	private Date bidEndDate;
	
	private Seller seller;

	public Product() {
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

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription
				+ ", detailedDescription=" + detailedDescription + ", category=" + category + ", startingPrice="
				+ startingPrice + ", bidEndDate=" + bidEndDate + ", seller=" + seller + "]";
	}
	
	public static class Builder {
	    
	    private String id;
	    private String name;
	    private String shortDescription;
	    private String detailedDescription;
	    private Category category;
	    private Double startingPrice;
	    private Date bidEndDate;
	    private Seller seller;

	    public Builder() {    
	    }
	      
	    Builder(String id, String name, String shortDescription, String detailedDescription, Category category, Double startingPrice, Date bidEndDate, Seller seller) {    
	      this.id = id; 
	      this.name = name; 
	      this.shortDescription = shortDescription; 
	      this.detailedDescription = detailedDescription; 
	      this.category = category; 
	      this.startingPrice = startingPrice; 
	      this.bidEndDate = bidEndDate; 
	      this.seller = seller;             
	    }
	        
	    public Builder id(String id){
	      this.id = id;
	      return Builder.this;
	    }

	    public Builder name(String name){
	      this.name = name;
	      return Builder.this;
	    }

	    public Builder shortDescription(String shortDescription){
	      this.shortDescription = shortDescription;
	      return Builder.this;
	    }

	    public Builder detailedDescription(String detailedDescription){
	      this.detailedDescription = detailedDescription;
	      return Builder.this;
	    }

	    public Builder category(Category category){
	      this.category = category;
	      return Builder.this;
	    }

	    public Builder startingPrice(Double startingPrice){
	      this.startingPrice = startingPrice;
	      return Builder.this;
	    }

	    public Builder bidEndDate(Date bidEndDate){
	      this.bidEndDate = bidEndDate;
	      return Builder.this;
	    }

	    public Builder seller(Seller seller){
	      this.seller = seller;
	      return Builder.this;
	    }

	    public Product build() {
	        return new Product(this);
	    }
	  }

	  private Product(Builder builder) {
	    this.id = builder.id; 
	    this.name = builder.name; 
	    this.shortDescription = builder.shortDescription; 
	    this.detailedDescription = builder.detailedDescription; 
	    this.category = builder.category; 
	    this.startingPrice = builder.startingPrice; 
	    this.bidEndDate = builder.bidEndDate; 
	    this.seller = builder.seller;     
	  }

}
