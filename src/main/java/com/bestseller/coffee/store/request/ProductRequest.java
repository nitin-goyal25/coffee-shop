package com.bestseller.coffee.store.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

	@NotEmpty
	private String name;

	@NotEmpty
	private String cost;

}
