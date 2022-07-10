package com.fse.eauction.service;

import java.util.ArrayList;
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
import com.fse.eauction.model.CreateProductRequest;
import com.fse.eauction.model.CreateProductRequestSellerEntity;
import com.fse.eauction.model.PlaceBidRequest;
import com.fse.eauction.model.Product;
import com.fse.eauction.repository.BidRepository;
import com.fse.eauction.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")                      
public class ProductServiceTests {

	@InjectMocks
	ProductServiceImpl productService;
	
	@Mock
	BidRepository bidRepository;

	@Mock
	ProductRepository productRepository;

	@BeforeEach
	protected void setUp() {

	}

	@Test
	public void test_save_success() throws Exception {
		CreateProductRequest createProductRequest = new CreateProductRequest();
		createProductRequest.setSeller(new CreateProductRequestSellerEntity());
		
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product.Builder().id("123").build());
		PlaceBidRequest placeBidRequest = new PlaceBidRequest();
		placeBidRequest.setProductId("123");
		Assertions.assertEquals("123", productService.save(createProductRequest));
	}

	@Test
	public void test_delete_productNotFound() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.empty());
		Mockito.when(bidRepository.findAllBidsOnProduct("123")).thenReturn(List.of(new Bid()));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.delete("123"));
	}

	@Test
	public void test_delete_bidEndDateCrossed() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() - 10000)).build()));
		Mockito.when(bidRepository.findAllBidsOnProduct("123")).thenReturn(List.of(new Bid()));

		Assertions.assertThrows(ResourceOperationNotPermittedException.class, () -> productService.delete("123"));
	}

	@Test
	public void test_delete_bidExistsOnProduct() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findAllBidsOnProduct("123")).thenReturn(List.of(new Bid()));

		Assertions.assertThrows(ResourceOperationNotPermittedException.class, () -> productService.delete("123"));
	}

	@Test
	public void test_delete_success() throws Exception {
		Mockito.when(productRepository.findById("123")).thenReturn(Optional.of(new Product.Builder().id("123").bidEndDate(new Date(new Date().getTime() + 10000)).build()));
		Mockito.when(bidRepository.findAllBidsOnProduct("123")).thenReturn(new ArrayList<>());
		Mockito.doNothing().when(productRepository).delete(Mockito.any(Product.class));
		Assertions.assertDoesNotThrow(() -> productService.delete("123"));
	}
}
