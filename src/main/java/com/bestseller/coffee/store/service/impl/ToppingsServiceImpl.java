package com.bestseller.coffee.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestseller.coffee.store.constant.CoffeeStoreConstants;
import com.bestseller.coffee.store.entity.Toppings;
import com.bestseller.coffee.store.exception.ToppingNotFoundException;
import com.bestseller.coffee.store.repository.ToppingsRepository;
import com.bestseller.coffee.store.request.ErrorCodes;
import com.bestseller.coffee.store.request.ToppingRequest;
import com.bestseller.coffee.store.response.ToppingResponse;
import com.bestseller.coffee.store.service.ToppingsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ToppingsServiceImpl implements ToppingsService {
	@Autowired
	ToppingsRepository toppingsRepository;

	@Override
	public List<ToppingResponse> getAllToppings() {
		log.debug("Inside getAllToppings service");
		List<Toppings> toppingsList = toppingsRepository.findAll();
		List<ToppingResponse> toppings = new ArrayList<ToppingResponse>();
		if (!toppingsList.isEmpty()) {
			toppingsList.forEach(toppingObject -> {
				ToppingResponse topping = ToppingResponse.builder().name(toppingObject.getName())
						.cost(toppingObject.getCost()).id(toppingObject.getId()).build();
				toppings.add(topping);
			});
		}
		return toppings;
	}

	@Override
	public ToppingResponse createTopping(ToppingRequest request) {
		log.debug("Inside createTopping service with topping {}", request.getName());
		Toppings topping = null;
		Optional<Toppings> toppingsObject = toppingsRepository.findByNameIgnoreCase(request.getName());
		if (toppingsObject.isPresent()) {
			log.info("topping with name {} already exists.", request.getName());
			topping = toppingsObject.get();
		} else {
			topping = Toppings.builder().name(request.getName()).cost(request.getCost()).build();
			topping = toppingsRepository.save(topping);
		}
		return ToppingResponse.builder().id(topping.getId()).name(topping.getName()).cost(topping.getCost()).build();
	}

	@Override
	public ToppingResponse updateTopping(ToppingRequest request, Long id) throws ToppingNotFoundException {
		log.debug("Inside updateTopping service with topping {}", request.getName());
		Optional<Toppings> toppingObject = getTopping(id);
		if (!toppingObject.isPresent()) {
			log.info(CoffeeStoreConstants.ERROR_MESSAGE_STRING_TOPPING_NOT_FOUND, id);
			throw new ToppingNotFoundException(ErrorCodes.TOPPING_NOT_FOUND);
		} else {
			Toppings topping = toppingObject.get();
			BeanUtils.copyProperties(request, topping);
			toppingsRepository.save(topping);
			return ToppingResponse.builder().id(topping.getId()).name(topping.getName()).cost(topping.getCost())
					.build();
		}
	}

	@Override
	public ToppingResponse getToppingById(Long id) throws ToppingNotFoundException {
		log.debug("Inside getToppingById service with id {}", id);
		Optional<Toppings> toppingObject = getTopping(id);
		if (!toppingObject.isPresent()) {
			log.info(CoffeeStoreConstants.ERROR_MESSAGE_STRING_TOPPING_NOT_FOUND, id);
			throw new ToppingNotFoundException(ErrorCodes.TOPPING_NOT_FOUND);
		} else {
			Toppings topping = toppingObject.get();
			return ToppingResponse.builder().id(topping.getId()).name(topping.getName()).cost(topping.getCost())
					.build();
		}
	}

	@Override
	public void deleteTopping(Long id) throws ToppingNotFoundException {
		log.debug("Inside deleteTopping service with id {}", id);
		Optional<Toppings> toppingObject = getTopping(id);
		if (!toppingObject.isPresent()) {
			log.info(CoffeeStoreConstants.ERROR_MESSAGE_STRING_TOPPING_NOT_FOUND, id);
			throw new ToppingNotFoundException(ErrorCodes.TOPPING_NOT_FOUND);
		} else {
			Toppings topping = toppingObject.get();
			topping.setIsDeleted(CoffeeStoreConstants.STRING_YES);
			toppingsRepository.save(topping);
		}
	}

	public Optional<Toppings> getTopping(Long id) {
		return toppingsRepository.findByIdAndIsDeleted(id, CoffeeStoreConstants.STRING_NO);
	}

	@Override
	public List<String> getMostUsedToppings() {
		log.debug("Inside getMostUsedToppings service");
		return toppingsRepository.maxUsedToppings();
	}

}
