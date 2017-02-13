package com.eugene.ex04_2.src.main.java.com.restfully.shop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eugene.ex04_2.src.main.java.com.restfully.shop.domain.Customer;



public class CustomerService {
	// don't use Integer, not work
	private static Map<Long, Customer> customerDB = new ConcurrentHashMap<>();

	public CustomerService() {
		customerDB.put(1L, new Customer(1, "Bill","Burke", "256 Clarendon Street", "Boston", "MA", "02115", "USA"));
		customerDB.put(2L, new Customer(2, "Eugene","Tan", "1 Main St", "Boston", "MA", "02115", "USA"));
	}

	public List<Customer> getAllCustomers() {
		return new ArrayList<Customer>(customerDB.values());
	}
	
	public Customer getCustomer(long id) {
		Customer customer = customerDB.get(id);
		if (customer == null) {
			System.out.println("Customer with id " + id + " not found");
		}
		return customer;
	}

	public Customer addCustomer(Customer customer) {
		customer.setId(customerDB.size() + 1);
		customerDB.put(customer.getId(), customer);
		return customer;
	}

	public Customer updateCustomer(Customer customer) {
		if (customer.getId() <= 0) {
			return null;
		}
		customerDB.put(customer.getId(), customer);
		return customer;
	}

	public Customer removeCustomer(long id) {
		return customerDB.remove(id);
	}
}
