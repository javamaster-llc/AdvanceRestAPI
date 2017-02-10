package com.eugene.advancerestapi.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers1")
public class CustomerResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessage1() {
		return "Hello World";
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String getMessage2() {
		return "POST";
	}
	
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public String getMessage3() {
		return "PUT";
	}
	
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	public String getMessage4() {
		return "DELETE";
	}	
}
