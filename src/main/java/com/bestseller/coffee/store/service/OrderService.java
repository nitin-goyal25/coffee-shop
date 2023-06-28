package com.bestseller.coffee.store.service;

import javax.validation.Valid;

import com.bestseller.coffee.store.request.OrderDTO;

public interface OrderService {

	OrderDTO addProducts(@Valid OrderDTO request);

	OrderDTO calculateFinalDiscountedAmount(@Valid OrderDTO request);

}
