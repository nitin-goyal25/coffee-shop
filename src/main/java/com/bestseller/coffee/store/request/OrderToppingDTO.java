package com.bestseller.coffee.store.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderToppingDTO {

	private String name;

	private long id;

}
