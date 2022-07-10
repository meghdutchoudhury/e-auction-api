package com.fse.eauction.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.model.Product;
import com.fse.eauction.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	@Lazy
	ProductRepository productRepository;

	@Override
	public List<Product> getProducts() {
		logger.info("Entered getProducts()");
		List<Product> products = new ArrayList<>();
		try {
			products = productRepository.findAll();
		} catch (Exception e) {
			logger.error("Exception in getProducts(): {}", e);
			throw new BusinessException("Error while getting products: " + e.getMessage());
		}
		logger.info("Completed getProducts()");
		return products;
	}

}
