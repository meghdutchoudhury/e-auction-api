package com.fse.eauction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fse.eauction.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>  {

}
