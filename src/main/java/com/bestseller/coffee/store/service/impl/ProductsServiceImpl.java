package com.bestseller.coffee.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestseller.coffee.store.constant.CoffeeStoreConstants;
import com.bestseller.coffee.store.entity.Product;
import com.bestseller.coffee.store.exception.ProductNotFoundException;
import com.bestseller.coffee.store.repository.ProductRepository;
import com.bestseller.coffee.store.request.ErrorCodes;
import com.bestseller.coffee.store.request.ProductRequest;
import com.bestseller.coffee.store.response.DrinksResponse;
import com.bestseller.coffee.store.service.ProductsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<DrinksResponse> getAllProducts() {
		log.debug("Inside getAllProducts service");
		List<Product> productList = productRepository.findAll();
		List<DrinksResponse> drinks = new ArrayList<DrinksResponse>();
		if (!productList.isEmpty()) {
			productList.forEach(product -> {
				DrinksResponse drink = DrinksResponse.builder().name(product.getName()).cost(product.getCost())
						.id(product.getId()).build();
				drinks.add(drink);
			});
		}
		return drinks;
	}

	@Override
	public DrinksResponse createProduct(ProductRequest request) {
		log.debug("Inside createProduct service with product {}", request.getName());
		Product product = null;
		Optional<Product> productobject = productRepository.findByNameIgnoreCase(request.getName());
		if (productobject.isPresent()) {
			log.info("product with name {} already exists.", request.getName());
			product = productobject.get();
		} else {
			product = Product.builder().name(request.getName()).cost(request.getCost()).build();
			product = productRepository.save(product);
		}
		return DrinksResponse.builder().id(product.getId()).name(product.getName()).cost(product.getCost()).build();
	}

	@Override
	public DrinksResponse updateProduct(ProductRequest request, Long id) throws ProductNotFoundException {
		log.debug("Inside updateProduct service with product {}", request.getName());
		Optional<Product> productObject = getProduct(id);
		if (!productObject.isPresent()) {
			log.info(CoffeeStoreConstants.ERROR_MESSAGE_STRING_PRODUCT_NOT_FOUND, id);
			throw new ProductNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
		} else {
			Product product = productObject.get();
			BeanUtils.copyProperties(request, product);
			productRepository.save(product);
			return DrinksResponse.builder().id(product.getId()).name(product.getName()).cost(product.getCost()).build();
		}
	}

	@Override
	public DrinksResponse getPrdouctById(Long id) throws ProductNotFoundException {
		log.debug("Inside getPrdouctById service with id {}", id);
		Optional<Product> productObject = getProduct(id);
		if (!productObject.isPresent()) {
			log.info(CoffeeStoreConstants.ERROR_MESSAGE_STRING_PRODUCT_NOT_FOUND, id);
			throw new ProductNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
		} else {
			Product product = productObject.get();
			return DrinksResponse.builder().id(product.getId()).name(product.getName()).cost(product.getCost()).build();
		}
	}

	@Override
	public void deleteProduct(Long id) throws ProductNotFoundException {
		log.debug("Inside deleteProduct service with id {}", id);
		Optional<Product> productObject = getProduct(id);
		if (!productObject.isPresent()) {
			log.info(CoffeeStoreConstants.ERROR_MESSAGE_STRING_PRODUCT_NOT_FOUND, id);
			throw new ProductNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
		} else {
			Product product = productObject.get();
			product.setIsDeleted(CoffeeStoreConstants.STRING_YES);
			productRepository.save(product);
		}
	}

	public Optional<Product> getProduct(Long id) {
		return productRepository.findByIdAndIsDeleted(id, CoffeeStoreConstants.STRING_NO);
	}

}
