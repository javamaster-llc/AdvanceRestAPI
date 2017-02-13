package com.eugene.ex04_2.src.main.java.com.restfully.shop.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class ShoppingApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public ShoppingApplication() {
		singletons.add(new CustomerResource());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}