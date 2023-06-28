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

import com.bestseller.coffee.store.exception.ToppingNotFoundException;
import com.bestseller.coffee.store.request.ToppingRequest;
import com.bestseller.coffee.store.response.ToppingResponse;
import com.bestseller.coffee.store.service.ToppingsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/toppings", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ToppingsController {

	@Autowired
	ToppingsService toppingsService;

	@GetMapping
	public ResponseEntity<List<ToppingResponse>> getAllToppings() {
		log.debug("Inside getAllToppings");
		List<ToppingResponse> toppings = toppingsService.getAllToppings();
		return new ResponseEntity<>(toppings, HttpStatus.OK);
	}

	@PostMapping
	ResponseEntity<ToppingResponse> createTopping(@RequestBody @Valid ToppingRequest request) {
		log.debug("Inside createTopping");
		ToppingResponse topping = toppingsService.createTopping(request);
		return new ResponseEntity<>(topping, HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<ToppingResponse> getToppingById(@PathVariable("id") final Long id)
			throws ToppingNotFoundException {
		log.debug("Inside controller getToppingById Id: {}", id);
		ToppingResponse topping = toppingsService.getToppingById(id);
		return new ResponseEntity<>(topping, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	void deleteTopping(@PathVariable("id") final Long id) throws ToppingNotFoundException {
		log.debug("Inside controller deleteTopping Id : {} ", id);
		toppingsService.deleteTopping(id);
	}

	@PutMapping("{id}")
	public ResponseEntity<ToppingResponse> updateTopping(@PathVariable("id") final Long id,
			@RequestBody @Valid ToppingRequest request) throws ToppingNotFoundException {
		log.debug("Inside controller updateTopping Id: {}", id);
		ToppingResponse topping = toppingsService.updateTopping(request, id);
		return new ResponseEntity<>(topping, HttpStatus.OK);
	}

	@GetMapping("/mostUsedToppings")
	public ResponseEntity<List<String>> getMostUsedToppings() {
		log.debug("Inside controller getMostUsedToppings");
		List<String> toppingList = toppingsService.getMostUsedToppings();
		return new ResponseEntity<>(toppingList, HttpStatus.OK);
	}

}
