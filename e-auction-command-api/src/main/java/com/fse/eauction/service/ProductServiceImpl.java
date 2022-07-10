package com.fse.eauction.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.exception.ResourceOperationNotPermittedException;
import com.fse.eauction.model.Bid;
import com.fse.eauction.model.CreateProductRequest;
import com.fse.eauction.model.Product;
import com.fse.eauction.model.Seller;
import com.fse.eauction.repository.BidRepository;
import com.fse.eauction.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	@Lazy
	ProductRepository productRepository;

	@Autowired
	@Lazy
	BidRepository bidRepository;

	@Override
	public String save(CreateProductRequest createProductRequest) {
		logger.info("Entered save() with request {}", createProductRequest);

		String productId = "";
		try {
			Seller seller = new Seller.Builder()
					.firstName(createProductRequest.getSeller().getFirstName())
					.lastName(createProductRequest.getSeller().getLastName())
					.address(createProductRequest.getSeller().getAddress())
					.city(createProductRequest.getSeller().getCity())
					.state(createProductRequest.getSeller().getState())
					.pin(createProductRequest.getSeller().getPin())
					.phone(createProductRequest.getSeller().getPhone())
					.email(createProductRequest.getSeller().getEmail())
					.build();

			Product product = new Product.Builder()
					.name(createProductRequest.getName())
					.shortDescription(createProductRequest.getShortDescription())
					.detailedDescription(createProductRequest.getDetailedDescription())
					.category(createProductRequest.getCategory())
					.startingPrice(createProductRequest.getStartingPrice())
					.bidEndDate(createProductRequest.getBidEndDate())
					.seller(seller)
					.build();

			product = productRepository.save(product);
			productId = product.getId();
		} catch (Exception e) {
			logger.error("Exception in saveProduct(): {}", e);
			throw new BusinessException("Error while saving product: " + e.getMessage());
		}
		logger.info("Completed save() with response {}", productId);
		return productId;
	}

	@Override
	public void delete(String productId) {
		logger.info("Entered delete() with productId {}", productId);
		try {
			Optional<Product> product = productRepository.findById(productId);
			List<Bid> existingBids = bidRepository.findAllBidsOnProduct(productId);

			if(!product.isPresent()) {
				throw new ResourceNotFoundException("Product does not exist");
			} else if(product.get().getBidEndDate().before(new Date())) {
				throw new ResourceOperationNotPermittedException("Can not delete product after bid end date");
			} else if(existingBids!=null && existingBids.size() > 0) {
				throw new ResourceOperationNotPermittedException("Can not delete product since a bid has already been placed on it");
			}
			productRepository.delete(product.get());
		} catch (ResourceNotFoundException | ResourceOperationNotPermittedException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception in deleteProduct(): {}", e);
			throw new BusinessException("Error while deleting product: " + e.getMessage());
		}
		logger.info("Completed delete()");
	}

}
