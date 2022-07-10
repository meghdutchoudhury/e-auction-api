package com.fse.eauction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.exception.ResourceOperationNotPermittedException;
import com.fse.eauction.model.Bid;
import com.fse.eauction.model.BidSortKey;
import com.fse.eauction.model.GetBidsResponse;
import com.fse.eauction.model.GetBidsResponseBidEntity;
import com.fse.eauction.model.GetBidsResponseBuyerEntity;
import com.fse.eauction.model.GetBidsResponseProductEntity;
import com.fse.eauction.model.Product;
import com.fse.eauction.model.SortDirection;
import com.fse.eauction.repository.BidRepository;
import com.fse.eauction.repository.ProductRepository;
import com.google.common.collect.Lists;

@Service
public class BidServiceImpl implements BidService {

	Logger logger = LoggerFactory.getLogger(BidServiceImpl.class);

	@Autowired
	@Lazy
	BidRepository bidRepository;

	@Autowired
	@Lazy
	ProductRepository productRepository;

	@Override
	public GetBidsResponse findAllBidsOnProduct(String productId, String filterBy, BidSortKey sortKey, SortDirection sortDirection, Integer pageNumber, Integer pageSize) {
		logger.info("Entered findAllBidsOnProduct({})", productId);
		GetBidsResponse getBidsResponse = new GetBidsResponse();
		try {
			Optional<Product> productOptional = productRepository.findById(productId);
			if(!productOptional.isPresent()) {
				throw new ResourceNotFoundException("Product does not exist");
			}

			Product product = productOptional.get();

			List<Bid> bids = bidRepository.findAllBidsOnProduct(productId);
			logger.debug("Processing bids: {}", bids);

			List<GetBidsResponseBidEntity> getBidsResponseBidEntityList = new ArrayList<>();

			if(bids!=null) {

				getBidsResponseBidEntityList = bids.stream().map(bid -> {

					GetBidsResponseBuyerEntity getBidsResponseBuyerEntity = new GetBidsResponseBuyerEntity.Builder()
							.firstName(bid.getBuyer().getFirstName())
							.lastName(bid.getBuyer().getLastName())
							.address(bid.getBuyer().getAddress())
							.city(bid.getBuyer().getCity())
							.state(bid.getBuyer().getState())
							.pin(bid.getBuyer().getPin())
							.phone(bid.getBuyer().getPhone())
							.email(bid.getBuyer().getEmail())
							.build();

					return new GetBidsResponseBidEntity.Builder()
							.id(bid.getId())
							.amount(bid.getAmount())
							.buyer(getBidsResponseBuyerEntity)
							.build();
				}).collect(Collectors.toList());

				// Filter and sort
				getBidsResponseBidEntityList = getBidsResponseBidEntityList.stream()
						.filter(b -> {
							return (b.getAmount()!=null && b.getAmount().toString().contains(filterBy)) ||
									(b.getBuyer().getFirstName()!=null && b.getBuyer().getFirstName().toLowerCase().contains(filterBy)) ||
									(b.getBuyer().getLastName()!=null && b.getBuyer().getLastName().toLowerCase().contains(filterBy)) ||
									(b.getBuyer().getAddress()!=null && b.getBuyer().getAddress().toLowerCase().contains(filterBy)) ||
									(b.getBuyer().getCity()!=null && b.getBuyer().getCity().toLowerCase().contains(filterBy)) ||
									(b.getBuyer().getState()!=null && b.getBuyer().getState().toLowerCase().contains(filterBy)) ||
									(b.getBuyer().getPin()!=null && b.getBuyer().getPin().contains(filterBy)) ||
									(b.getBuyer().getPhone()!=null && b.getBuyer().getPhone().contains(filterBy)) ||
									(b.getBuyer().getEmail()!=null && b.getBuyer().getEmail().toLowerCase().contains(filterBy));
						})
						.sorted((b1, b2) -> {
							int result = 0;
							if(sortKey.equals(BidSortKey.amount)) {
								result = b1.getAmount().compareTo(b2.getAmount());
							} else if(sortKey.equals(BidSortKey.firstName)) {
								result = b1.getBuyer().getFirstName().compareTo(b2.getBuyer().getFirstName());
							}  else if(sortKey.equals(BidSortKey.lastName)) {
								result = b1.getBuyer().getLastName().compareTo(b2.getBuyer().getLastName());
							}  else if(sortKey.equals(BidSortKey.address)) {
								result = b1.getBuyer().getAddress().compareTo(b2.getBuyer().getAddress());
							}  else if(sortKey.equals(BidSortKey.city)) {
								result = b1.getBuyer().getCity().compareTo(b2.getBuyer().getCity());
							}  else if(sortKey.equals(BidSortKey.state)) {
								result = b1.getBuyer().getState().compareTo(b2.getBuyer().getState());
							}  else if(sortKey.equals(BidSortKey.pin)) {
								result = b1.getBuyer().getPin().compareTo(b2.getBuyer().getPin());
							}  else if(sortKey.equals(BidSortKey.phone)) {
								result = b1.getBuyer().getPhone().compareTo(b2.getBuyer().getPhone());
							}  else if(sortKey.equals(BidSortKey.email)) {
								result = b1.getBuyer().getEmail().compareTo(b2.getBuyer().getEmail());
							}

							if(sortDirection.equals(SortDirection.desc)) {
								result = -result;
							}

							return result;
						})
						.collect(Collectors.toList());

				if(getBidsResponseBidEntityList.size() > 0) {
					// Paginate
					List<List<GetBidsResponseBidEntity>> getBidsResponseBidEntityListPages = Lists.partition(getBidsResponseBidEntityList, Math.min(pageSize, getBidsResponseBidEntityList.size()));
					if(pageNumber > getBidsResponseBidEntityListPages.size()) {
						getBidsResponseBidEntityList.clear();
					} else {
						getBidsResponseBidEntityList = getBidsResponseBidEntityListPages.get(pageNumber - 1);
					}
				}

			}

			GetBidsResponseProductEntity getBidsResponseProductEntity = new GetBidsResponseProductEntity.Builder()
					.id(product.getId())
					.name(product.getName())
					.shortDescription(product.getShortDescription())
					.detailedDescription(product.getDetailedDescription())
					.category(product.getCategory())
					.startingPrice(product.getStartingPrice())
					.bidEndDate(product.getBidEndDate())
					.build();

			getBidsResponse = new GetBidsResponse.Builder()
					.product(getBidsResponseProductEntity)
					.bids(getBidsResponseBidEntityList)
					.build();

		} catch (ResourceNotFoundException | ResourceOperationNotPermittedException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Exception in findAllBidsOnProduct(): {}", e);
			throw new BusinessException("Error while getting bids for product: " + e.getMessage());
		}

		logger.info("Completed findAllBidsOnProduct({}) with response: {}", productId, getBidsResponse);

		return getBidsResponse;
	}

}
