package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class UserBean {

	private String name;
	private String email;
	private String phone;
	private String dob;
	private String gender;
	private String role;
	private String createdDttm;
	private String updrDttm;
	private CartBean cart;
	private String dpPath;
	public String getDpPath() {
		return dpPath;
	}
	public void setDpPath(String dpPath) {
		this.dpPath = dpPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreatedDttm() {
		return createdDttm;
	}
	public void setCreatedDttm(String createdDttm) {
		this.createdDttm = createdDttm;
	}
	public String getUpdrDttm() {
		return updrDttm;
	}
	public void setUpdrDttm(String updrDttm) {
		this.updrDttm = updrDttm;
	}
	public CartBean getCart() {
		return cart;
	}
	public void setCart(CartBean cart) {
		this.cart = cart;
	}
	
	
	
}
