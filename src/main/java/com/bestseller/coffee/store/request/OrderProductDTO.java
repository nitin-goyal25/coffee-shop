package com.bestseller.coffee.store.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductDTO {

	private String name;

	private long id;

	private List<OrderToppingDTO> toppings;

	private double productAmount;

}
