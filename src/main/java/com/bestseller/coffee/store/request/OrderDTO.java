package com.bestseller.coffee.store.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

	private List<OrderProductDTO> products;

	private double cartAmount;

	private double discountedAmount;

}
