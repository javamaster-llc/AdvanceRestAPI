package com.eugene;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eugene.ex04_2.src.main.java.com.restfully.shop.domain.Customer;

public class StreamingOutputDemo {
	private static Map<Long, Customer> customerDB = new ConcurrentHashMap<>();
	private AtomicInteger idCounter = new AtomicInteger();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test1() {
		StreamingOutput string = get();
		System.out.println(string.toString());		
	}

	public StreamingOutput get() {
		return new StreamingOutput() {
			public void write(OutputStream output) throws IOException, WebApplicationException {
				output.write("hello world".getBytes());
			}
		};
	}

	@Test
	public void test2() {
		Response response = streamExample();
		System.out.println(response);
		if (response.getStatus() != 200)
			throw new RuntimeException("Failed to pass");		
	}

	public Response streamExample() {
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				Writer writer = new BufferedWriter(new OutputStreamWriter(os));
				writer.write("test me");
				writer.flush();
			}
		};
		return Response.ok(stream).build();
	}

	String xml = "<customer>" + "<first-name>Bill</first-name>" + "<last-name>Burke</last-name>"
			+ "<street>256 Clarendon Street</street>" + "<city>Boston</city>" + "<state>MA</state>" + "<zip>02115</zip>"
			+ "<country>USA</country>" + "</customer>";

	@Test
	public void test3() {
//		byte[] bytes = xml.getBytes();		                
		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		Response response = createCustomer(inputStream);
		System.out.println(response);
		if (response.getStatus() != 201)
			throw new RuntimeException("Failed to create");		
		
	}

	public Response createCustomer(InputStream is) {
		System.out.println("POST here");
		Customer customer = readCustomer(is);
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(), customer);
		System.out.println("Created customer " + customer.getId());
		return Response.created(URI.create("/customers/" + customer.getId())).build();
	}

	protected Customer readCustomer(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Customer cust = new Customer();
			if (root.getAttribute("id") != null && !root.getAttribute("id").trim().equals(""))
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
