package com.bestseller.coffee.store.service;

import java.util.List;

import com.bestseller.coffee.store.exception.ToppingNotFoundException;
import com.bestseller.coffee.store.request.ToppingRequest;
import com.bestseller.coffee.store.response.ToppingResponse;

public interface ToppingsService {

	List<ToppingResponse> getAllToppings();

	ToppingResponse createTopping(ToppingRequest request);

	ToppingResponse updateTopping(ToppingRequest request, Long id) throws ToppingNotFoundException;

	ToppingResponse getToppingById(Long id) throws ToppingNotFoundException;

	void deleteTopping(Long id) throws ToppingNotFoundException;

	List<String> getMostUsedToppings();

}
