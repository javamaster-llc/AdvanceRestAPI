package com.eugene.advancerestapi.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.eugene.advancerestapi.domain.Customer;
import com.eugene.advancerestapi.util.CustomerService;

@Path("/customers")
@Consumes({ "application/xml", "application/json" })
@Produces({ "application/xml", "application/json" })
public class CustomerResource {

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String getMessage1() { return
	 * "Hello World"; }
	 */

	CustomerService customerService = new CustomerService();

	@GET
//	@Produces(MediaType.APPLICATION_XML)
	public List<Customer> getCustomers() {
		System.out.println("GET");
		return customerService.getAllCustomers();
	}

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String getMessage2() { return
	 * "Hello World"; }
	 */

	@GET
	@Path("/{customerId}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("customerId") long id) {
		// System.out.println("GET ID " + id);
		return customerService.getCustomer(id);
	}

	@POST
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Customer addMessage(Customer customer) {
		System.out.println("POST");
		return customerService.addCustomer(customer);
	}

	@PUT
	@Path("/{customerId}")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Customer updateCustomer(@PathParam("customerId") long id, Customer customer) {
		customer.setId(id);
		return customerService.updateCustomer(customer);
	}

	/**
	 * Since 1 and 2 customers are created by contractors, the delete won't display the result.
	 * Always deleting after customers, e.g.3nd, 4th .... 
	 * @param id
	 */
	@DELETE
	@Path("/{customerId}")
	public void deleteCustomer(@PathParam("customerId") long id) {
		System.out.println("DELETE ID " + id);
		customerService.removeCustomer(id);
	}
}