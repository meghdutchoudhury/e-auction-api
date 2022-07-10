package com.fse.eauction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fse.eauction.model.Product;

@Service
public interface ProductService {
	
	List<Product> getProducts();
	
}
