package com.bestseller.coffee.store.web;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.bestseller.coffee.store.CoffeeStoreApplicationTests;

class ProductControllerTest extends CoffeeStoreApplicationTests {

	@Test
	void listProducts() throws Exception {
		mvc.perform((get("/products"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].name", hasItem("Black Coffee")));
	}

	@Test
	void getProductById() throws Exception {
		mvc.perform((get("/products/1"))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Black Coffee")));
	}

	@Test
	void getNonExistingProduct() throws Exception {
		mvc.perform((get("/products/666"))).andExpect(status().isNotFound());
	}

	@Test
	void createProduct() throws Exception {
		mvc.perform(post("/products/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"new product\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", greaterThan(0)));
	}

	@Test
	void nameAndCostMissing() throws Exception {
		mvc.perform(post("/products/").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void nameMissing() throws Exception {
		mvc.perform(post("/products/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"cost\" : \"5\"\r\n" + "}")).andExpect(status().isBadRequest());
	}

	@Test
	void createExistingProduct() throws Exception {
		mvc.perform(post("/products/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"Tea\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", greaterThan(0)));
	}

	@Test
	void costMissing() throws Exception {
		mvc.perform(post("/products/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"name\" : \"newproduct\"\r\n" + "}")).andExpect(status().isBadRequest());
	}

	@Test
	void deleteProduct() throws Exception {
		mvc.perform(delete("/products/2")).andExpect(status().isOk());
	}

	@Test
	void deleteNonExistingProduct() throws Exception {
		mvc.perform(delete("/products/666")).andExpect(status().isNotFound());
	}

	@Test
	void updateProduct() throws Exception {
		mvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"new product\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", greaterThan(0)));
	}

	@Test
	void updateNonExistingProduct() throws Exception {
		mvc.perform(put("/products/666").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"new product\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isNotFound());
	}

}
