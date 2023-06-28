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

public class ToppingControllerTest extends CoffeeStoreApplicationTests {

	@Test
	void listtoppings() throws Exception {
		mvc.perform((get("/toppings"))).andExpect(status().isOk()).andExpect(jsonPath("$.[*].name", hasItem("Milk")));
	}

	@Test
	void gettoppingById() throws Exception {
		mvc.perform((get("/toppings/5"))).andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo("Milk")));
	}

	@Test
	void getNonExistingtopping() throws Exception {
		mvc.perform((get("/toppings/666"))).andExpect(status().isNotFound());
	}

	@Test
	void createtopping() throws Exception {
		mvc.perform(post("/toppings/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"new topping\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", greaterThan(0)));
	}

	@Test
	void nameAndCostMissing() throws Exception {
		mvc.perform(post("/toppings/").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void nameMissing() throws Exception {
		mvc.perform(post("/toppings/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"cost\" : \"5\"\r\n" + "}")).andExpect(status().isBadRequest());
	}

	@Test
	void createExistingtopping() throws Exception {
		mvc.perform(post("/toppings/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"Milk\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", greaterThan(0)));
	}

	@Test
	void costMissing() throws Exception {
		mvc.perform(post("/toppings/").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"name\" : \"newtopping\"\r\n" + "}")).andExpect(status().isBadRequest());
	}

	@Test
	void deletetopping() throws Exception {
		mvc.perform(delete("/toppings/5")).andExpect(status().isOk());
	}

	@Test
	void deleteNonExistingtopping() throws Exception {
		mvc.perform(delete("/toppings/666")).andExpect(status().isNotFound());
	}

	@Test
	void updatetopping() throws Exception {
		mvc.perform(put("/toppings/5").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"new topping\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", greaterThan(0)));
	}

	@Test
	void updateNonExistingtopping() throws Exception {
		mvc.perform(put("/toppings/666").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "\r\n" + "    \"name\" : \"new topping\",\r\n" + "    \"cost\" : \"5\"\r\n" + "}"))
				.andExpect(status().isNotFound());
	}

	@Test
	void mostUsedToppings() throws Exception {
		mvc.perform(get("/toppings/mostUsedToppings")).andExpect(status().isOk());
	}

}
