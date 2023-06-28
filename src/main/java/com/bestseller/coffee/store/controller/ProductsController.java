package com.bestseller.coffee.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bestseller.coffee.store.exception.ProductNotFoundException;
import com.bestseller.coffee.store.request.ProductRequest;
import com.bestseller.coffee.store.response.DrinksResponse;
import com.bestseller.coffee.store.service.ProductsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProductsController {

	@Autowired
	ProductsService productsService;

	@GetMapping
	public ResponseEntity<List<DrinksResponse>> getAllProducts() {
		log.debug("Inside controller getAllProducts");
		List<DrinksResponse> drinks = productsService.getAllProducts();
		return new ResponseEntity<>(drinks, HttpStatus.OK);
	}

	@PostMapping
	ResponseEntity<DrinksResponse> createProdcut(@RequestBody @Valid ProductRequest request) {
		log.debug("Inside controller createProdcut");
		DrinksResponse product = productsService.createProduct(request);
		return new ResponseEntity<>(product, HttpStatus.CREATED);

	}

	@GetMapping("{id}")
	public ResponseEntity<DrinksResponse> getPrdouctById(@PathVariable("id") final Long id)
			throws ProductNotFoundException {
		log.debug("Inside controller getProductById Id: {}", id);
		DrinksResponse drink = productsService.getPrdouctById(id);
		return new ResponseEntity<>(drink, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	void deleteProduct(@PathVariable("id") final Long id) throws ProductNotFoundException {
		log.debug("Inside controller deleteProduct Id : {} ", id);
		productsService.deleteProduct(id);
	}

	@PutMapping("{id}")
	public ResponseEntity<DrinksResponse> updatePrdouct(@PathVariable("id") final Long id,
			@RequestBody @Valid ProductRequest request) throws ProductNotFoundException {
		log.debug("Inside controller updatePrdouct Id: {}", id);
		DrinksResponse drink = productsService.updateProduct(request, id);
		return new ResponseEntity<>(drink, HttpStatus.OK);
	}

}
