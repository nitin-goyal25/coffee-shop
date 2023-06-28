package com.bestseller.coffee.store.service;

import java.util.List;

import com.bestseller.coffee.store.exception.ProductNotFoundException;
import com.bestseller.coffee.store.request.ProductRequest;
import com.bestseller.coffee.store.response.DrinksResponse;

public interface ProductsService {

	List<DrinksResponse> getAllProducts();

	DrinksResponse createProduct(ProductRequest request);

	DrinksResponse updateProduct(ProductRequest request, Long id) throws ProductNotFoundException;

	DrinksResponse getPrdouctById(Long id) throws ProductNotFoundException;

	void deleteProduct(Long id) throws ProductNotFoundException;

}
