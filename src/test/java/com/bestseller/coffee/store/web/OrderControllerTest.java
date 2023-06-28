package com.bestseller.coffee.store.web;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.bestseller.coffee.store.CoffeeStoreApplicationTests;

class OrderControllerTest extends CoffeeStoreApplicationTests {

	@Test
	void addproductAndCalculateCartValue() throws Exception {
		mvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"products\" :[\r\n" + "        {\r\n"
						+ "            \"name\" : \"Black Coffee\",\r\n" + "            \"id\" : 1,\r\n"
						+ "            \"toppings\" : [\r\n" + "                  {\r\n"
						+ "                      \"name\" : \"Milk\",\r\n" + "                      \"id\" : 5\r\n"
						+ "                  }   \r\n" + "            ] \r\n" + "        }\r\n" + "    ]\r\n" + "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.cartAmount", greaterThan(0.0)));
	}

	@Test
	void calculateFinalPrice() throws Exception {
		mvc.perform(post("/order/finalize-order").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"products\": [\r\n" + "        {\r\n"
						+ "            \"name\": \"Black Coffee\",\r\n" + "            \"id\": 1,\r\n"
						+ "            \"toppings\": [\r\n" + "                {\r\n"
						+ "                    \"name\": \"Milk\",\r\n" + "                    \"id\": 5\r\n"
						+ "                }\r\n" + "            ],\r\n" + "            \"productAmount\": 6.0\r\n"
						+ "        },\r\n" + "        {\r\n" + "            \"name\": \"Latte\",\r\n"
						+ "            \"id\": 2,\r\n" + "            \"toppings\": [\r\n" + "                {\r\n"
						+ "                    \"name\": \"Hazelnut syrup\",\r\n" + "                    \"id\": 6\r\n"
						+ "                }\r\n" + "            ],\r\n" + "            \"productAmount\": 8.0\r\n"
						+ "        },\r\n" + "        {\r\n" + "            \"name\": \"Mocha\",\r\n"
						+ "            \"id\": 3,\r\n" + "            \"toppings\": [\r\n" + "                {\r\n"
						+ "                    \"name\": \"Chocolate sauce\",\r\n" + "                    \"id\": 7\r\n"
						+ "                }\r\n" + "            ],\r\n" + "            \"productAmount\": 11.0\r\n"
						+ "        }\r\n" + "    ],\r\n" + "    \"cartAmount\": 25.0\r\n" + "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.discountedAmount", greaterThan(0.0)));
	}

	@Test
	void calculateFinalPriceDiscountApplied() throws Exception {
		mvc.perform(post("/order/finalize-order").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"products\" :[\r\n" + "        {\r\n"
						+ "            \"name\" : \"Black Coffee\",\r\n" + "            \"id\" : 1,\r\n"
						+ "            \"toppings\" : [\r\n" + "                  {\r\n"
						+ "                      \"name\" : \"Milk\",\r\n" + "                      \"id\" : 5\r\n"
						+ "                  }   \r\n" + "            ], \r\n" + "          \"productAmount\": 14.0\r\n"
						+ "        }\r\n" + "    ],\r\n" + "    \"cartAmount\": 14.0\r\n" + "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.discountedAmount", greaterThan(0.0)));
	}

	@Test
	void calculateFinalPriceThreeProducts() throws Exception {
		mvc.perform(post("/order/finalize-order").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"products\": [\r\n" + "        {\r\n"
						+ "            \"name\": \"Black Coffee\",\r\n" + "            \"id\": 1,\r\n"
						+ "            \"toppings\": [\r\n" + "                {\r\n"
						+ "                    \"name\": \"Milk\",\r\n" + "                    \"id\": 5\r\n"
						+ "                }\r\n" + "            ],\r\n" + "            \"productAmount\": 1.0\r\n"
						+ "        },\r\n" + "        {\r\n" + "            \"name\": \"Latte\",\r\n"
						+ "            \"id\": 2,\r\n" + "            \"toppings\": [\r\n" + "                {\r\n"
						+ "                    \"name\": \"Hazelnut syrup\",\r\n" + "                    \"id\": 6\r\n"
						+ "                }\r\n" + "            ],\r\n" + "            \"productAmount\": 1.0\r\n"
						+ "        },\r\n" + "        {\r\n" + "            \"name\": \"Mocha\",\r\n"
						+ "            \"id\": 3,\r\n" + "            \"toppings\": [\r\n" + "                {\r\n"
						+ "                    \"name\": \"Chocolate sauce\",\r\n" + "                    \"id\": 7\r\n"
						+ "                }\r\n" + "            ],\r\n" + "            \"productAmount\": 1.0\r\n"
						+ "        }\r\n" + "    ],\r\n" + "    \"cartAmount\": 3.0\r\n" + "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.discountedAmount", greaterThan(0.0)));
	}

}
