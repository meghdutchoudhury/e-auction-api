package com.fse.eauction.controller;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
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

import com.fse.eauction.command.PlaceBidCommand;
import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.service.BidService;

@WebMvcTest(controllers = BidController.class)
@ActiveProfiles("test")
public class BidControllerTests {

	@MockBean
    CommandGateway commandGateway;

	@MockBean
	BidService bidService;
	
	@Autowired
	private MockMvc mvc;

	@BeforeEach
	protected void setUp() {

	}

	@Test
	public void test_placeBid_200() throws Exception {
		try {
		String uri = "/buyer/place-bid";
		String requestJson = "{ \"productId\": \"62a0784d5352606f90501152\", \"amount\": 999, \"placeBidRequestBuyerEntity\": { \"firstName\": \"Jacky\", \"lastName\": \"Doe\", \"address\": \"12, New Town\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9009001234\", \"email\": \"jack@doe.cop\" } }";
		Mockito.when(commandGateway.send(Mockito.any(PlaceBidCommand.class))).thenReturn(CompletableFuture.completedFuture("123"));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_placeBid_400() throws Exception {
		String uri = "/buyer/place-bid";
		String requestJson = "{ \"productId\": \"62a0784d5352606f90501152\", \"placeBidRequestBuyerEntity\": { \"firstName\": \"Jacky\", \"lastName\": \"Doe\", \"address\": \"12, New Town\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9009001234\", \"email\": \"jack@doe.cop\" } }";
		Mockito.when(commandGateway.send(Mockito.any(PlaceBidCommand.class))).thenReturn(CompletableFuture.completedFuture("123"));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_placeBid_404() throws Exception {
		String uri = "/buyer/place-bid";
		String requestJson = "{ \"productId\": \"62a0784d5352606f90501152\", \"amount\": 999, \"placeBidRequestBuyerEntity\": { \"firstName\": \"Jacky\", \"lastName\": \"Doe\", \"address\": \"12, New Town\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9009001234\", \"email\": \"jack@doe.cop\" } }";
		Mockito.when(commandGateway.send(Mockito.any(PlaceBidCommand.class))).thenThrow(new ResourceNotFoundException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_placeBid_500() throws Exception {
		String uri = "/buyer/place-bid";
		String requestJson = "{ \"productId\": \"62a0784d5352606f90501152\", \"amount\": 999, \"placeBidRequestBuyerEntity\": { \"firstName\": \"Jacky\", \"lastName\": \"Doe\", \"address\": \"12, New Town\", \"city\": \"Kolkata\", \"state\": \"WB\", \"pin\": \"700001\", \"phone\": \"9009001234\", \"email\": \"jack@doe.cop\" } }";
		Mockito.when(commandGateway.send(Mockito.any(PlaceBidCommand.class))).thenThrow(new BusinessException("Test fault"));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_updateBid_200() throws Exception {
		String uri = "/buyer/update-bid/123/john@doe.com/100";
		Mockito.doNothing().when(bidService).updateBidAmount("123", "john@doe.com", 100d);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();
		Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_updateBid_400() throws Exception {
		String uri = "/buyer/update-bid/123/john@doe.com/nonnumeric";
		Mockito.doNothing().when(bidService).updateBidAmount("123", "john@doe.com", 100d);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void test_updateBid_404() throws Exception {
		String uri = "/buyer/update-bid/123/john@doe.com/100";
		Mockito.doThrow(new ResourceNotFoundException()).when(bidService).updateBidAmount("123", "john@doe.com", 100d);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
	}

}
