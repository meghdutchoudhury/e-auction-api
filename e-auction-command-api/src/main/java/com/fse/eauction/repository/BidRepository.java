package com.fse.eauction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.fse.eauction.model.Bid;

public interface BidRepository extends MongoRepository<Bid, String>  {

	@Query("{ 'productId' : ?0 }")
	public List<Bid> findAllBidsOnProduct(String productId);

	@Query("{'productId' : ?0, 'buyer.email': ?1}")
	public Optional<Bid> findBidOnProductForUser(String productId, String buyerEmailId);

}
