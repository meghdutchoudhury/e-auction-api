package com.fse.eauction.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.model.CreateProductRequest;
import com.fse.eauction.service.ProductService;

@WebMvcTest(controllers = ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTests {

	@MockBean
	ProductService productService;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	protected void setUp() {

	}

	@Test
	public void test_addProduct_200() throws Exception {
		String uri = "/seller/add-product";
		String requestJson = "{ \"name\": \"Myx23\", \"shortDescription\": \"My Product Short Description\", \"detailedDescription\": \"My Product Detailed Description\", \"category\": \"Painting\", \"startingPrice\": 100, \"bidEndDate\": \"2023-06-05T08:04:50.825Z\", \"seller\": { \"id\": \"1\", \"firstName\": \"Johne\", \"lastName\": \"Doe\", \"address\": \"12, New Market\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9001234567\", \"email\": \"john@doe.com.qo\" } }";
		Mockito.when(productService.save(Mockito.any(CreateProductRequest.class))).thenReturn("123");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
		Assertions.assertEquals("123", mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void test_addProduct_400() throws Exception {
		String uri = "/seller/add-product";
		String requestJson = "{ \"shortDescription\": \"My Product Short Description\", \"detailedDescription\": \"My Product Detailed Description\", \"category\": \"Painting\", \"startingPrice\": 100, \"bidEndDate\": \"2023-06-05T08:04:50.825Z\", \"seller\": { \"id\": \"1\", \"firstName\": \"Johne\", \"lastName\": \"Doe\", \"address\": \"12, New Market\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9001234567\", \"email\": \"john@doe.com.qo\" } }";
		Mockito.when(productService.save(Mockito.any(CreateProductRequest.class))).thenReturn("123");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_addProduct_404() throws Exception {
		String uri = "/seller/add-product-wrong-path";
		String requestJson = "{ \"shortDescription\": \"My Product Short Description\", \"detailedDescription\": \"My Product Detailed Description\", \"category\": \"Painting\", \"startingPrice\": 100, \"bidEndDate\": \"2023-06-05T08:04:50.825Z\", \"seller\": { \"id\": \"1\", \"firstName\": \"Johne\", \"lastName\": \"Doe\", \"address\": \"12, New Market\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9001234567\", \"email\": \"john@doe.com.qo\" } }";
		Mockito.when(productService.save(Mockito.any(CreateProductRequest.class))).thenReturn("123");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_addProduct_500() throws Exception {
		String uri = "/seller/add-product";
		String requestJson = "{ \"name\": \"Myx23\", \"shortDescription\": \"My Product Short Description\", \"detailedDescription\": \"My Product Detailed Description\", \"category\": \"Painting\", \"startingPrice\": 100, \"bidEndDate\": \"2023-06-05T08:04:50.825Z\", \"seller\": { \"id\": \"1\", \"firstName\": \"Johne\", \"lastName\": \"Doe\", \"address\": \"12, New Market\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9001234567\", \"email\": \"john@doe.com.qo\" } }";
		Mockito.when(productService.save(Mockito.any(CreateProductRequest.class))).thenReturn(null);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_deleteProduct_200() throws Exception {
		String uri = "/seller/delete/123";
		Mockito.doNothing().when(productService).delete("123");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_deleteProduct_404() throws Exception {
		String uri = "/seller/delete/123";
		Mockito.doThrow(new ResourceNotFoundException()).when(productService).delete("123");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
	}

}
