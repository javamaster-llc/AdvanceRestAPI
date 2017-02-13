package com.eugene.ex04_2.src.test.java.com.restfully.shop.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 */
public class CustomerResourceTest {
	private static Client client;

	@BeforeClass
	public static void initClient() {
		client = ClientBuilder.newClient();
	}

	@AfterClass
	public static void closeClient() {
		client.close();
	}

	@Test
	public void testCustomerResource() throws Exception {
		System.out.println("*** Create a new Customer ***");

		String xml = "<customer>" + "<first-name>Bill</first-name>"
				+ "<last-name>Burke</last-name>"
				+ "<street>256 Clarendon Street</street>"
				+ "<city>Boston</city>" + "<state>MA</state>"
				+ "<zip>02115</zip>" + "<country>USA</country>" + "</customer>";
		
		String JSON_DATA =
		    	"{"
		    	 +" \"city\": \"Boston\","
		    	 +" \"country\": \"USA\","
		    	 +" \"firstName\": \"Bill\","
		    	 +" \"id\": \"3\","
		    	 +" \"lastName\": \"Burke\","
		    	 +" \"state\": \"MA\","
		    	 +" \"street\": \"256 Clarendon Street\","
		    	 +" \"zip\": \"02115\""
		    	+"}";
			  		
	    // Exercise
//	    response = customerTarget.request().post(Entity.json(JSON_DATA));
	    
		Response response = client
				.target("http://localhost:8280/advancerestapi/customers")
				.request()
				.post(Entity.json(JSON_DATA));
//		.post(Entity.xml(xml));

		if (response.getStatus() != 200)
			throw new RuntimeException("Failed to create");
		String location = response.getLocation().toString();
		System.out.println("Location: " + location);
		response.close();

		System.out.println("*** GET Created Customer **");
		String customer = client.target(location).request().get(String.class);
		System.out.println(customer);
		System.out.println("**** Use first-name ***");
		customer = client
				.target("http://localhost:8080/services/customers/Bill-Burke")
				.request().get(String.class);
		System.out.println(customer);
	}
}