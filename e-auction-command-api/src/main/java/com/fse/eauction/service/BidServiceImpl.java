package com.fse.eauction.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.exception.ResourceOperationNotPermittedException;
import com.fse.eauction.model.Bid;
import com.fse.eauction.model.Buyer;
import com.fse.eauction.model.PlaceBidRequest;
import com.fse.eauction.model.PlaceBidResponse;
import com.fse.eauction.model.Product;
import com.fse.eauction.repository.BidRepository;
import com.fse.eauction.repository.ProductRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

@Service
public class BidServiceImpl implements BidService {

	Logger logger = LoggerFactory.getLogger(BidServiceImpl.class);

	@Autowired
	@Lazy
	BidRepository bidRepository;

	@Autowired
	@Lazy
	ProductRepository productRepository;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Value("${spring.data.mongodb.host:}")
	String mongoHost;
	
	@Value("${spring.data.mongodb.port:}")
	String mongoPort;

	@Value("${spring.data.mongodb.embedded.storagerepl-set-name:rs0}")
	String mongoReplicaSet;

	@Override
	public PlaceBidResponse place(PlaceBidRequest placeBidRequest) {

		PlaceBidResponse placeBidResponse = new PlaceBidResponse();

		try {
			Optional<Product> productOptional = productRepository.findById(placeBidRequest.getProductId());
			if(!productOptional.isPresent()) {
				throw new ResourceNotFoundException("Product does not exist");
			}

			Product product = productOptional.get();

			if(product.getBidEndDate().before(new Date())) {
				throw new ResourceOperationNotPermittedException("Can not bid on product after bid end date");
			}

			List<Bid> existingBids = bidRepository.findAllBidsOnProduct(placeBidRequest.getProductId());
			if(existingBids!=null && existingBids.stream().anyMatch(b -> b.getBuyer().getEmail().equalsIgnoreCase(placeBidRequest.getPlaceBidRequestBuyerEntity().getEmail()))) {
				throw new ResourceOperationNotPermittedException("Buyer already has a bid on the product");
			}

			Buyer buyerEntity = new Buyer.Builder()
					.firstName(placeBidRequest.getPlaceBidRequestBuyerEntity().getFirstName())
					.lastName(placeBidRequest.getPlaceBidRequestBuyerEntity().getLastName())
					.address(placeBidRequest.getPlaceBidRequestBuyerEntity().getAddress())
					.city(placeBidRequest.getPlaceBidRequestBuyerEntity().getCity())
					.state(placeBidRequest.getPlaceBidRequestBuyerEntity().getState())
					.pin(placeBidRequest.getPlaceBidRequestBuyerEntity().getPin())
					.phone(placeBidRequest.getPlaceBidRequestBuyerEntity().getPhone())
					.email(placeBidRequest.getPlaceBidRequestBuyerEntity().getEmail())
					.build();

			Bid bidEntity = new Bid.Builder()
					.productId(placeBidRequest.getProductId())
					.amount(placeBidRequest.getAmount())
					.buyer(buyerEntity)
					.build();

			Bid savedBidEntity = bidRepository.save(bidEntity);

			placeBidResponse.setBidId(savedBidEntity.getId());
		} catch (ResourceNotFoundException | ResourceOperationNotPermittedException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception in placeBid(): {}", e);
			throw new BusinessException("Error while placing bid: " + e.getMessage());
		}
		return placeBidResponse;
	}

	@Override
	public void updateBidAmount(String productId, String buyerEmailId, Double newBidAmount) {
		logger.info("Entered updateBidAmount()");
		try {
			Optional<Product> productOptional = productRepository.findById(productId);
			if(!productOptional.isPresent()) {
				throw new ResourceNotFoundException("Product does not exist");
			}

			Product product = productOptional.get();

			if(product.getBidEndDate().before(new Date())) {
				throw new ResourceOperationNotPermittedException("Can not update bid amount after product bid end date");
			}

			Optional<Bid> bid = bidRepository.findBidOnProductForUser(productId, buyerEmailId);
			if(!bid.isPresent()) {
				throw new ResourceNotFoundException("Bid does not exist");
			}
			bid.get().setAmount(newBidAmount);

			if("cloud".equalsIgnoreCase(activeProfile)) {
				logger.info("Setting up custom MongoTemplate with retry writes disabled");
				MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
						.applyConnectionString(new ConnectionString("mongodb://" + mongoHost + ":" + mongoPort + "/?replicaSet=" + mongoReplicaSet))
						.retryWrites(false)
						.build();
				new MongoTemplate(MongoClients.create(mongoClientSettings), "test").save(bid.get());
			} else {
				bidRepository.save(bid.get());
			}

		} catch (ResourceNotFoundException | ResourceOperationNotPermittedException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception in updateBidAmount(): {}", e);
			throw new BusinessException("Error while updating bid amount" + e.getMessage());
		}
	}

}
