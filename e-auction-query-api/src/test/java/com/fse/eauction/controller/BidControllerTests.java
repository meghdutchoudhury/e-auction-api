package com.fse.eauction.controller;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryGateway;
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

import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.model.GetBidsResponse;

@WebMvcTest(controllers = BidController.class)
@ActiveProfiles("test")
public class BidControllerTests {

	@MockBean
	QueryGateway queryGateway;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	protected void setUp() {

	}

	@Test
	public void test_findAllBidsOnProduct_200() throws Exception {
		String uri = "/seller/show-bids/123";
		Mockito.when(queryGateway.query(Mockito.any(), Mockito.eq(GetBidsResponse.class))).thenReturn(CompletableFuture.completedFuture(new GetBidsResponse()));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_findAllBidsOnProduct_400() throws Exception {
		String uri = "/seller/show-bids/123?pageNumber=-1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_findAllBidsOnProduct_500() throws Exception {
		String uri = "/seller/show-bids/123";
		Mockito.when(queryGateway.query(Mockito.any(), Mockito.eq(GetBidsResponse.class))).thenThrow(new BusinessException("Testing service failure"));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_findAllBidsOnProduct_404() throws Exception {
		String uri = "/seller/show-bids/123";
		Mockito.when(queryGateway.query(Mockito.any(), Mockito.eq(GetBidsResponse.class))).thenThrow(new ResourceNotFoundException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
	}

}
