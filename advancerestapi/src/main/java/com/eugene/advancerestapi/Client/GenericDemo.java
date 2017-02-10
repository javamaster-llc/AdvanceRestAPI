package com.eugene.advancerestapi.Client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.eugene.advancerestapi.domain.Customer;

public class GenericDemo {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		List<Customer> customer = client.target("http://localhost:8080/advancerestapi/webapi/")
				.path("customers")
//				.queryParam("year", 2015)
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Customer>>() { });
				
		System.out.println(customer);
	}
}
