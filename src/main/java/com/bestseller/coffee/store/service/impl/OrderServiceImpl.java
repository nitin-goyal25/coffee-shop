package com.bestseller.coffee.store.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestseller.coffee.store.constant.CoffeeStoreConstants;
import com.bestseller.coffee.store.entity.Product;
import com.bestseller.coffee.store.repository.ProductRepository;
import com.bestseller.coffee.store.repository.ToppingsRepository;
import com.bestseller.coffee.store.request.OrderDTO;
import com.bestseller.coffee.store.request.OrderProductDTO;
import com.bestseller.coffee.store.request.OrderToppingDTO;
import com.bestseller.coffee.store.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ToppingsRepository toppingsRepository;

	@Override
	public OrderDTO addProducts(@Valid OrderDTO request) {
		double totalAmount = 0;
		List<Double> totalAmountList = new ArrayList<>();
		totalAmountList.add(totalAmount);
		if (!request.getProducts().isEmpty()) {
			List<OrderProductDTO> productList = request.getProducts();
			productList.forEach(product -> {
				double individualOrderamount = getAmountOfIndividualProduct(product);
				product.setProductAmount(individualOrderamount);
				double amount = totalAmountList.get(0);
				amount = amount + individualOrderamount;
				totalAmountList.set(0, amount);
			});
		}
		request.setCartAmount(totalAmountList.get(0));
		return request;
	}

	private double getAmountOfIndividualProduct(OrderProductDTO product) {
		double amount = 0;
		Optional<Product> productObject = productRepository.findById(product.getId());
		if (!productObject.isPresent()) {
			log.info("product is not found with given id {}", product.getId());
		} else {
			amount = amount + Double.valueOf(productObject.get().getCost());
			if (!product.getToppings().isEmpty()) {
				List<OrderToppingDTO> toppingList = product.getToppings();
				List<Long> toppingIdList = toppingList.stream().map(OrderToppingDTO::getId).toList();
				List<String> toppingCostList = toppingsRepository.findPriceForEachTopping(toppingIdList);
				updateToppingUsedCount(toppingIdList);
				amount = amount + toppingCostList.stream().mapToDouble(Double::valueOf).sum();
			}
		}
		return amount;
	}

	private void updateToppingUsedCount(List<Long> toppingIdList) {
		toppingIdList.forEach(toppingid -> toppingsRepository.updateToppingUsedCount(toppingid));

	}

	@Override
	public OrderDTO calculateFinalDiscountedAmount(@Valid OrderDTO request) {
		List<Double> indivaidualOrderPriceList = null;
		if (!request.getProducts().isEmpty()) {
			indivaidualOrderPriceList = request.getProducts().stream().map(OrderProductDTO::getProductAmount).collect(Collectors.toList());
			request.setDiscountedAmount(getDiscountedAmount(indivaidualOrderPriceList, request.getCartAmount()));
		}
		return request;
	}

	private double getDiscountedAmount(List<Double> indivaidualOrderPriceList, double totalAmount) {
		if (indivaidualOrderPriceList.size() >= CoffeeStoreConstants.ELIGIBLE_CART_SIZE_DISCOUNT
				&& totalAmount > CoffeeStoreConstants.ELIGIBLE_CART_AMOUNT_DISCOUNT) {
			double minusOneAmount = getAmountMinusOneOrder(indivaidualOrderPriceList, totalAmount);
			double minusOfferedDiscountAmount = getMinusOfferedDiscountedAmount(totalAmount);
			totalAmount = minusOneAmount > minusOfferedDiscountAmount ? minusOfferedDiscountAmount : minusOneAmount;
		} else if (indivaidualOrderPriceList.size() >= CoffeeStoreConstants.ELIGIBLE_CART_SIZE_DISCOUNT) {
			double minusOneAmount = getAmountMinusOneOrder(indivaidualOrderPriceList, totalAmount);
			totalAmount = minusOneAmount;
		} else if (totalAmount > CoffeeStoreConstants.ELIGIBLE_CART_AMOUNT_DISCOUNT) {
			double minusOfferedDiscountAmount = getMinusOfferedDiscountedAmount(totalAmount);
			totalAmount = minusOfferedDiscountAmount;
		}
		return totalAmount;
	}

	private double getMinusOfferedDiscountedAmount(double totalAmount) {
		return (totalAmount * ((100 - CoffeeStoreConstants.OFFERED_DISCOUNT) / 100.0f));
	}

	private double getAmountMinusOneOrder(List<Double> indivaidualOrderPriceList, Double totalAmount) {
		Collections.sort(indivaidualOrderPriceList);
		totalAmount = totalAmount - indivaidualOrderPriceList.get(0);
		return totalAmount;
	}

}
