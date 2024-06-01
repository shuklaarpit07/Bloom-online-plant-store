package com.example.demo.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.daoimpl.UserDao;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.ProductsDto;
import com.example.demo.model.ProductsBean;
import com.example.demo.model.UserBean;

@Service
public class UserService {
	@Autowired
	UserBean userBean;
	@Autowired
	UserDao userDao;
	@Autowired
	ProductsBean productsBean;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public UserBean fetchLoggedInUserDetails(LoginDto user) throws Exception
	{
		try {
			userBean=userDao.fetchLoggedInUserDetails(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return userBean;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public UserBean fetchJwtUserDetails(String email) throws Exception
	{
		try {
			userBean=userDao.fetchJwtUserDetails(email);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return userBean;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ProductsBean>fetchAllProducts()throws Exception{
		List<ProductsBean> products = new LinkedList<>();
		try {
			products=userDao.fetchAllProducts();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return products;
	}
	/**
	 * 
	 * @param user
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public String addToCart(UserBean user , String productId)throws Exception {
		String msg="";
		try {
			msg= userDao.addToCart(Integer.parseInt(productId), user.getEmail());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return msg;
	}
	
	public UserBean updateUserCart(String email,ProductsDto cart) throws Exception
	{
		try {
			String msg=userDao.updateUserCart(email, cart);
			if(msg=="cartUpdated") {
				userBean=userDao.fetchJwtUserDetails(email);
			}
			else {
				throw new Exception("unable To Update Cart");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return userBean;
	}
	

}
