package com.fse.eauction.service;

import org.springframework.stereotype.Service;

import com.fse.eauction.model.CreateProductRequest;

@Service
public interface ProductService {
	String save(CreateProductRequest createProductRequest);
	void delete(String productId);
}
