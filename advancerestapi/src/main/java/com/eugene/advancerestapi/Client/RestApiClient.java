package com.eugene.advancerestapi.Client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class RestApiClient {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();

/*		WebTarget baseTarget =  client.target("http://localhost:8080/restapi/webapi/");
		WebTarget messsageTarget = baseTarget.path("messages");
		WebTarget singleMessageTarget = messsageTarget.path("{messageId}");
		
		Message messsage1 = singleMessageTarget
				.resolveTemplate("messageId", "1")
				.request(MediaType.APPLICATION_JSON)
				.get(Message.class);
		
		Message messsage2 = singleMessageTarget.resolveTemplate("messageId", "2").request(MediaType.APPLICATION_JSON).get(Message.class);
		
		System.out.println(messsage1.getMessage());
		System.out.println(messsage2.getMessage());
		
		Response response =  client.target("http://localhost:8080/restapi/webapi/messages/1").request().get();
		Message messsage = response.readEntity(Message.class);
		System.out.println(messsage.getMessage());
		
		Message newMessage = new Message(4, "My new Message from restapi clinet", "Eugene");
		
		Response postResponse = messsageTarget.request().post(Entity.json(newMessage));
		
		Message createMessage = postResponse.readEntity(Message.class);
		
		System.out.println(createMessage.getMessage());*/
		
	}
}
