package com.bestseller.coffee.store.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToppingResponse {

	private Long id;
	private String name;
	private String cost;

}
