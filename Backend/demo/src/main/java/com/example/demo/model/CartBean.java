package com.example.demo.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CartBean {
	private String email;
	private List<ProductsBean> products;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<ProductsBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductsBean> products) {
		this.products = products;
	}
	
	
	

}
