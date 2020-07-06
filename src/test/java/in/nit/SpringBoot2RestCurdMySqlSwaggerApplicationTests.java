package in.nit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@DisplayName("SPRING BOOT PRODUCT TEST CASE")
public class SpringBoot2RestCurdMySqlSwaggerApplicationTests {

	private Logger log = LoggerFactory.getLogger(SpringBoot2RestCurdMySqlSwaggerApplicationTests.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET ONE PRODUcT BY ID")
	@Disabled
	void testGetOne() throws Exception {
		//1. Construct Http Request Object
		MockHttpServletRequestBuilder request =
				MockMvcRequestBuilders.get("/rest/product/one/5");

		//2. execute request and get Result
		MvcResult result=mockMvc.perform(request).andReturn();

		//3. Read Response from Result
		MockHttpServletResponse response = result.getResponse();

		//4. Assert Response Data
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if(!response.getContentType().contains("application/json")) {
			fail("May be not JSON Data!!");
		}
		log.info(response.getContentAsString());
	}

	@Test
	@Disabled
	@DisplayName("FETCH ALL PRODUCTS")
	public void testGetAll() throws Exception {
		//1. Construct Http Request Object
		MockHttpServletRequestBuilder request =
				MockMvcRequestBuilders.get("/rest/product/all");

		//2. execute request and get Result
		MvcResult result=mockMvc.perform(request).andReturn();

		//3. Read Response from Result
		MockHttpServletResponse response = result.getResponse();

		//4. Assert Response Data
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if(!response.getContentType().contains("application/json")) {
			fail("May be not JSON Data!!");
		}

	}


	@Test
	@Disabled
	@DisplayName("SAVE PRODUCT")
	public void testSave() throws Exception {
		//1. Construct Http Request Object
		MockHttpServletRequestBuilder request =
				MockMvcRequestBuilders.post("/rest/product/save")
				.header("Content-Type", "application/json")
				.content("{\"prodCode\":\"ABC\",\"prodCost\":500.0,\"prodDiscount\":350.0,\"prodGst\":200.0}");

		//2. execute request and get Result
		MvcResult result=mockMvc.perform(request).andReturn();

		//3. Read Response from Result
		MockHttpServletResponse response = result.getResponse();

		//4. Assert Response Data
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if(!response.getContentAsString().contains("created Successfully")) {
			fail("Product may not be saved!!");
		}
		log.info(response.getContentAsString());
	}
	
	
	@Test
	@Disabled
	@DisplayName("UPDATE PRODUCT")
	public void testUpdate() throws Exception {
		//1. Construct Http Request Object
		MockHttpServletRequestBuilder request =
				MockMvcRequestBuilders.put("/rest/product/modify")
				.header("Content-Type", "application/json")
				.content("{\"prodId\":6,\"prodCode\":\"NEW BOOK\",\"prodCost\":550.0,\"prodDiscount\":380.0,\"prodGst\":220.0}");

		//2. execute request and get Result
		MvcResult result=mockMvc.perform(request).andReturn();

		//3. Read Response from Result
		MockHttpServletResponse response = result.getResponse();

		//4. Assert Response Data
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if(!response.getContentAsString().contains("Updated!")) {
			fail("Product may not be saved!!");
		}
		log.info(response.getContentAsString());
	}
	
	@Test
	@DisplayName("REMOVE PRODUCT BY ID")
	public void testDelete() throws Exception {
		//1. Create Request
		MockHttpServletRequestBuilder request =
		MockMvcRequestBuilders.delete("/rest/product/remove/7");
		
		//2. Execute Request and GetResult
		MvcResult result = mockMvc.perform(request).andReturn();
		
		//3. Get Response from Result
		MockHttpServletResponse resp = result.getResponse();
		
		//4. Assert Details
		assertEquals(HttpStatus.OK.value(), resp.getStatus());
		assertNotNull(resp.getContentAsString());
		if(!resp.getContentAsString().contains("Deleted")) {
			fail("Product might not be deleted!!");
		}
	}
	
	
}
