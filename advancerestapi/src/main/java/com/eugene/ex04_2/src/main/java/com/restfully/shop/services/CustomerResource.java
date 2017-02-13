package com.eugene.ex04_2.src.main.java.com.restfully.shop.services;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eugene.ex04_2.src.main.java.com.restfully.shop.domain.Customer;

@Path("/customers")
public class CustomerResource {

//	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
	private static Map<Long, Customer> customerDB = new ConcurrentHashMap<>();
	private AtomicInteger idCounter = new AtomicInteger();

	
	CustomerService customerService = new CustomerService();

/*	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response streamExample() {
	  StreamingOutput stream = new StreamingOutput() {
	    @Override
	    public void write(OutputStream os) throws IOException,
	    WebApplicationException {
	      Writer writer = new BufferedWriter(new OutputStreamWriter(os));
	      writer.write("hello world");
	      writer.flush();
	    }
	  };
	  return Response.ok(stream).build();
	}*/
	
	 @GET   
	 @Produces("text/plain")   
	 public StreamingOutput get() {  
		 return new StreamingOutput() { 
			 public void write(OutputStream output) throws IOException,
			 WebApplicationException {
				 output.write("hello world!".getBytes());
			}      
		 };  
	 }	 
	 
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
/*	public List<Customer> getCustomers() {
		System.out.println("GET");
		return customerService.getAllCustomers();
	}*/

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String getMessage2() { return
	 * "Hello World"; }
	 */
		
/*	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getString() {
		return "Hi ... ";
	}

	@POST
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public Customer addMessage(Customer customer) {
		System.out.println("POST");
		return customerService.addCustomer(customer);
	}*/
	
	@POST
	@Consumes("application/xml")
//	@Consumes("application/json")
	public Response createCustomer(InputStream is) {
		System.out.println("POST here");
		Customer customer = readCustomer(is);
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(), customer);
		System.out.println("Created customer " + customer.getId());
		return Response.created(URI.create("/customers/" + customer.getId())).build();
	}

	@GET
	@Path("{id : \\d+}")
	@Produces("application/xml")
	public StreamingOutput getCustomer(@PathParam("id") int id) {
		final Customer customer = customerDB.get(id);
		if (customer == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputCustomer(outputStream, customer);
			}
		};
	}

	@GET
	@Path("{first : [a-zA-Z]+}-{last:[a-zA-Z]+}")
	@Produces("application/xml")
	public StreamingOutput getCustomerFirstLast(
			@PathParam("first") String first, @PathParam("last") String last) 
	{
		Customer found = null;
		for (Customer cust : customerDB.values()) {
			if (cust.getFirstName().equals(first) && cust.getLastName().equals(last)) {
				found = cust;
				break;
			}
		}
		if (found == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		final Customer customer = found;
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputCustomer(outputStream, customer);
			}
		};
	}

	@PUT
	@Path("{id : \\d+}")
	@Consumes("application/xml")
	public void updateCustomer(@PathParam("id") int id, InputStream is) {
		Customer update = readCustomer(is);
		Customer current = customerDB.get(id);
		if (current == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		current.setFirstName(update.getFirstName());
		current.setLastName(update.getLastName());
		current.setStreet(update.getStreet());
		current.setState(update.getState());
		current.setZip(update.getZip());
		current.setCountry(update.getCountry());
	}

	protected void outputCustomer(OutputStream os, Customer cust)
			throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println("<customer id=\"" + cust.getId() + "\">");
		writer.println("   <first-name>" + cust.getFirstName()
				+ "</first-name>");
		writer.println("   <last-name>" + cust.getLastName() + "</last-name>");
		writer.println("   <street>" + cust.getStreet() + "</street>");
		writer.println("   <city>" + cust.getCity() + "</city>");
		writer.println("   <state>" + cust.getState() + "</state>");
		writer.println("   <zip>" + cust.getZip() + "</zip>");
		writer.println("   <country>" + cust.getCountry() + "</country>");
		writer.println("</customer>");
	}

	protected Customer readCustomer(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Customer cust = new Customer();
			if (root.getAttribute("id") != null
					&& !root.getAttribute("id").trim().equals(""))
				cust.setId(Integer.valueOf(root.getAttribute("id")));
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("first-name")) {
					cust.setFirstName(element.getTextContent());
				} else if (element.getTagName().equals("last-name")) {
					cust.setLastName(element.getTextContent());
				} else if (element.getTagName().equals("street")) {
					cust.setStreet(element.getTextContent());
				} else if (element.getTagName().equals("city")) {
					cust.setCity(element.getTextContent());
				} else if (element.getTagName().equals("state")) {
					cust.setState(element.getTextContent());
				} else if (element.getTagName().equals("zip")) {
					cust.setZip(element.getTextContent());
				} else if (element.getTagName().equals("country")) {
					cust.setCountry(element.getTextContent());
				}
			}
			return cust;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
}