package org.javaworld.cmsbackend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.javaworld.cmsbackend.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsBackEndApplicationTests {

	private MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	public void contextLoads() {

	}

	@Test
	public void getProductsList() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String uri = "http://localhost:8080/api/products";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Product[] productlist = mapFromJson(content, Product[].class);
		assertTrue(productlist.length > 0);
	}

	public <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
