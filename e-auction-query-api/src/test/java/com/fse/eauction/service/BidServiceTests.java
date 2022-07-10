package com.fse.eauction.service;

import java.util.Arrays;
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
import com.fse.eauction.model.Bid;
import com.fse.eauction.model.BidSortKey;
import com.fse.eauction.model.Buyer;
import com.fse.eauction.model.Product;
import com.fse.eauction.model.SortDirection;
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
	public void test_findAllBidsOnProduct_productNotFound() throws Exception {
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> bidService.findAllBidsOnProduct("123", "", BidSortKey.amount, SortDirection.asc, 1, 1));
	}

	@Test
	public void test_findAllBidsOnProduct_noBids() throws Exception {		
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").build()));
		Mockito.when(bidRepository.findAllBidsOnProduct(Mockito.anyString())).thenReturn(null);
		Assertions.assertEquals(0, bidService.findAllBidsOnProduct("123", "email.com", BidSortKey.amount, SortDirection.asc, 1, 1).getBids().size());
	}

	@Test
	public void test_findAllBidsOnProduct_success_filter() throws Exception {
		Bid bid = new Bid.Builder().amount(100d).buyer(new Buyer.Builder()
				.firstName("John")
				.lastName("Doe")
				.address("address")
				.city("city")
				.state("state")
				.pin("pin")
				.phone("phone")
				.email("email@email.com")
				.build()).build();
		
		Mockito.when(bidRepository.findAllBidsOnProduct(Mockito.anyString())).thenReturn(List.of(bid, bid));
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").build()));
		
		Assertions.assertEquals(1, bidService.findAllBidsOnProduct("123", "email.com", BidSortKey.amount, SortDirection.asc, 1, 1).getBids().size());
		Assertions.assertEquals(0, bidService.findAllBidsOnProduct("123", "nomatch", BidSortKey.amount, SortDirection.asc, 1, 1).getBids().size());
		Assertions.assertEquals(0, bidService.findAllBidsOnProduct("123", "email.com", BidSortKey.amount, SortDirection.asc, 100, 1).getBids().size());
	}

	@Test
	public void test_findAllBidsOnProduct_success_sortDirection() throws Exception {
		Bid bid = new Bid.Builder().amount(100d).buyer(new Buyer.Builder()
				.firstName("John")
				.lastName("Doe")
				.address("address")
				.city("city")
				.state("state")
				.pin("pin")
				.phone("phone")
				.email("email@email.com")
				.build()).build();
		
		Mockito.when(bidRepository.findAllBidsOnProduct(Mockito.anyString())).thenReturn(List.of(bid, bid));
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").build()));
		
		Assertions.assertEquals(1, bidService.findAllBidsOnProduct("123", "email.com", BidSortKey.amount, SortDirection.desc, 1, 1).getBids().size());
	}

	@Test
	public void test_findAllBidsOnProduct_success_sortKey() throws Exception {
		Bid bid = new Bid.Builder().amount(100d).buyer(new Buyer.Builder()
				.firstName("John")
				.lastName("Doe")
				.address("address")
				.city("city")
				.state("state")
				.pin("pin")
				.phone("phone")
				.email("email@email.com")
				.build()).build();
		
		Mockito.when(bidRepository.findAllBidsOnProduct(Mockito.anyString())).thenReturn(List.of(bid, bid));
		Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new Product.Builder().id("123").build()));
		
		Arrays.stream(BidSortKey.values()).forEach(k -> Assertions.assertEquals(1, bidService.findAllBidsOnProduct("123", "", k, SortDirection.asc, 1, 1).getBids().size()));
	}

}
