package com.eugene;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class CustomerResourceTest {

	Client client = ClientBuilder.newClient();

	WebTarget baseTarget =  client.target("http://localhost:8280/advancerestapi/");
	WebTarget customerTarget = baseTarget.path("customers");
	WebTarget singleCustomerTarget = customerTarget.path("{customerId}");
    // Exercise	get single record	    
    WebTarget testCustomerTarget = singleCustomerTarget.resolveTemplate("customerId", "3");	    	
	
	Response response;
	
	private final static String JSON_DATA =
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
	  
	@Test
	public void testPOST() throws Exception {

	    // Exercise
	    response = customerTarget.request().post(Entity.json(JSON_DATA));

	    // Verify
	    assertThat(response.getStatus(), is(200));
	    response.close();
	}
	
	@Test
	public void testGET() throws Exception {

	    // Exercise	get all
	    response = customerTarget.request().get();
	    // Verify
	    assertThat(response.getStatus(), is(200));

	    response.close(); 
	}

	@Test
	public void testPUT() throws Exception {

	    // Exercise
	    response = testCustomerTarget.request().put(Entity.json(JSON_DATA));

	    // Verify
	    assertThat(response.getStatus(), is(200));
	    response.close();
	}
	
	@Test
	public void testDELETE() throws Exception {

	    // Exercise
	    response = testCustomerTarget.request().delete();

	    // Verify
	    assertThat(response.getStatus(), is(204));
	    response.close();
	}
}