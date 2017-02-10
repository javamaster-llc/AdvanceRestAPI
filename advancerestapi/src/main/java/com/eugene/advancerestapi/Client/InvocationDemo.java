package com.eugene.advancerestapi.Client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InvocationDemo {

	public static void main(String[] args) {
		
		InvocationDemo demo = new InvocationDemo();
		Invocation invocation = demo.prepareRequestForMessageByYear(2015);
		Response response = invocation.invoke();
		
		System.out.println(response.getStatus());

	}
	
	public Invocation prepareRequestForMessageByYear(int year) {
		Client client = ClientBuilder.newClient();
		
/*		WebTarget baseTarget =  client.target("http://localhost:8080/restapi/webapi/");
		WebTarget messsageTarget = baseTarget.path("messages");
		
		return messsageTarget
				.queryParam("year", year)
				.request(MediaType.APPLICATION_JSON)
				.buildGet();*/
		
		return client.target("http://localhost:8080/restapi/webapi/")
				.path("messages")
				.queryParam("year", year)
				.request(MediaType.APPLICATION_JSON)
				.buildGet();
	}

}
