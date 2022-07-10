package com.fse.eauction.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.eauction.model.Product;
import com.fse.eauction.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Validated
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@RequestMapping(path = "/seller/products", method = RequestMethod.GET)
	@Operation(summary = "getProducts", description = "Fetch all products")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product found with bids", content = @Content(schema = @Schema(implementation = List.class))), 
	})
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<List<Product>>(productService.getProducts(), HttpStatus.OK);
	}

}
