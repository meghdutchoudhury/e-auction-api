package com.fse.eauction.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.exception.ResourceOperationNotPermittedException;
import com.fse.eauction.model.Bid;
import com.fse.eauction.model.Buyer;
import com.fse.eauction.model.PlaceBidRequest;
import com.fse.eauction.model.PlaceBidRequestBuyerEntity;
import com.fse.eauction.model.Product;
import com.fse.eauction.repository.BidRepository;
import com.fse.eauction.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)   
@ActiveProfiles("test")                      
public class BidServiceTests {

	@InjectMocks
	BidServiceImpl bidService;
	
	@Mock
	BidRepository bidRepository;

	@Mock
	ProductRepository productRepository;

	@BeforeEach
	protected void setUp() {

	}

	@Test
	public void test_place_productNotFound() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.empty());
		PlaceBidRequest placeBidRequest = new PlaceBidRequest();
		placeBidRequest.setProductId("123");
		Assertions.assertThrows(ResourceNotFoundException.class, () -> bidService.place(placeBidRequest));
	}

	@Test
	public void test_place_bidEndDateCrossed() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() - 10000)).build()));
		PlaceBidRequest placeBidRequest = new PlaceBidRequest();
		placeBidRequest.setProductId("123");
		Assertions.assertThrows(ResourceOperationNotPermittedException.class, () -> bidService.place(placeBidRequest));
	}

	@Test
	public void test_place_bidAlreadyPresent() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findAllBidsOnProduct("123")).thenReturn(List.of(new Bid.Builder().buyer(new Buyer.Builder().email("john@doe.com").build()).build()));
		
		PlaceBidRequestBuyerEntity placeBidRequestBuyerEntity = new PlaceBidRequestBuyerEntity();
		placeBidRequestBuyerEntity.setEmail("john@doe.com");
		
		PlaceBidRequest placeBidRequest = new PlaceBidRequest();
		placeBidRequest.setProductId("123");
		placeBidRequest.setPlaceBidRequestBuyerEntity(placeBidRequestBuyerEntity);
		
		Assertions.assertThrows(ResourceOperationNotPermittedException.class, () -> bidService.place(placeBidRequest));
	}

	@Test
	public void test_place_success() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findAllBidsOnProduct("123")).thenReturn(List.of(new Bid.Builder().buyer(new Buyer.Builder().email("john@doe.com").build()).build()));
		Mockito.when(bidRepository.save(Mockito.any(Bid.class))).thenReturn(new Bid.Builder().id("200").build());
		
		PlaceBidRequestBuyerEntity placeBidRequestBuyerEntity = new PlaceBidRequestBuyerEntity();
		placeBidRequestBuyerEntity.setEmail("jane@doe.com");
		
		PlaceBidRequest placeBidRequest = new PlaceBidRequest();
		placeBidRequest.setProductId("123");
		placeBidRequest.setPlaceBidRequestBuyerEntity(placeBidRequestBuyerEntity);
		
		Assertions.assertEquals("200", bidService.place(placeBidRequest).getBidId());
	}

	@Test
	public void test_place_bidNotFound() throws Exception {		
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findBidOnProductForUser("123", "john@doe.com")).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> bidService.updateBidAmount("123", "john@doe.com", 100d));
	}

	@Test
	public void test_updateBidAmount_productNotFound() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> bidService.updateBidAmount("123", "john@doe.com", 100d));
	}

	@Test
	public void test_updateBidAmount_bidNotFound() throws Exception {		
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findBidOnProductForUser("123", "john@doe.com")).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> bidService.updateBidAmount("123", "john@doe.com", 100d));
	}

	@Test
	public void test_updateBidAmount_bidEndDateCrossed() throws Exception {		
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() - 10000)).build()));
		Assertions.assertThrows(ResourceOperationNotPermittedException.class, () -> bidService.updateBidAmount("123", "john@doe.com", 100d));
	}

	@Test
	public void test_updateBidAmount_success() throws Exception {		
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findBidOnProductForUser("123", "john@doe.com")).thenReturn(Optional.of(new Bid()));
		Assertions.assertDoesNotThrow(() -> bidService.updateBidAmount("123", "john@doe.com", 100d));
	}
}
