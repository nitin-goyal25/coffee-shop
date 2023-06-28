package com.bestseller.coffee.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestseller.coffee.store.request.OrderDTO;
import com.bestseller.coffee.store.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping
	ResponseEntity<OrderDTO> addProducts(@RequestBody @Valid OrderDTO request) {
		log.debug("Inside controller addProductInCart");
		OrderDTO response = orderService.addProducts(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("finalize-order")
	ResponseEntity<OrderDTO> calculateFinalAmount(@RequestBody @Valid OrderDTO request) {
		log.debug("Inside controller calculateFinalAmount");
		OrderDTO response = orderService.calculateFinalDiscountedAmount(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
