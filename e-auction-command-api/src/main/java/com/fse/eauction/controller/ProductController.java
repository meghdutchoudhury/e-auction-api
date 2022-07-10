package com.fse.eauction.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.eauction.model.CreateProductRequest;
import com.fse.eauction.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;

	@RequestMapping(path = "/seller/add-product", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "addProduct", description = "Adds a new product")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "201", description = "Product created", content = @Content(schema = @Schema(implementation = String.class))), 
	        @ApiResponse(responseCode = "400", description = "Invalid input"), 
	        @ApiResponse(responseCode = "500", description = "Failed to add new product")
	})	
	public ResponseEntity<String> addProduct(@Validated @RequestBody CreateProductRequest createProductRequest) {
		logger.debug("Entered addProduct() with request {}", createProductRequest);
		String productId = productService.save(createProductRequest);
		logger.debug("Completed addProduct() with productId {}", productId);
		return new ResponseEntity<String>(productId, productId !=null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(path = "/seller/delete/{productId}", method = RequestMethod.DELETE)
	@Operation(summary = "deleteProduct", description = "Deletes the product")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Contact created", content = @Content(schema = @Schema(implementation = Void.class))), 
	        @ApiResponse(responseCode = "404", description = "Product does not exist"),
	        @ApiResponse(responseCode = "500", description = "Failed to delete product")
	})	
	public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
		productService.delete(productId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
