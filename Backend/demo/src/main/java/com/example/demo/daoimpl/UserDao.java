package com.example.demo.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demo.constants.SQLConstants;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.ProductsDto;
import com.example.demo.model.CartBean;
import com.example.demo.model.ProductsBean;
import com.example.demo.model.UserBean;

@Component
public class UserDao {
	@Autowired
	public UserBean userBean;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	@Autowired
	ProductsBean productsBean;
	@Autowired
	CartBean cartBean;

	public UserBean fetchLoggedInUserDetails(LoginDto user) throws Exception {
		try {
		String sql = SQLConstants.FETCH_LOGGED_IN_USER_DETAILS;
		userBean = this.jdbcTemplate.queryForObject(sql, new RowMapper<UserBean>() {
			public UserBean mapRow(ResultSet rs, int arg1) throws SQLException {
				UserBean bean = new UserBean();
				bean.setName(rs.getString("name"));
				bean.setEmail(rs.getString("email"));
				bean.setPhone(rs.getString("phone"));
				bean.setGender(rs.getString("gender"));
				bean.setRole(rs.getString("role"));
				bean.setDob(rs.getString("dob"));
				bean.setDpPath(rs.getString("path"));
				
				return bean;

			}
		}, new Object[] { 1, user.getUsername(), user.getPassword() });
		cartBean = fetchUserCart(userBean.getEmail());
		userBean.setCart(cartBean);
		}
		catch(Exception ex) {
		   System.out.println("ERROR in UserDao.fetchLoggedInUserDetails : \t"+ ex.getMessage());
		   throw new Exception(ex.getMessage());
		   
		}
		
		return userBean;

	}
	public UserBean fetchJwtUserDetails(String email ) throws Exception {
		try {
		String sql = SQLConstants.FETCH_JWT_USER_DETAILS;
		userBean = this.jdbcTemplate.queryForObject(sql, new RowMapper<UserBean>() {
			public UserBean mapRow(ResultSet rs, int arg1) throws SQLException {
				UserBean bean = new UserBean();
				bean.setName(rs.getString("name"));
				bean.setEmail(rs.getString("email"));
				bean.setPhone(rs.getString("phone"));
				bean.setGender(rs.getString("gender"));
				bean.setRole(rs.getString("role"));
				bean.setDob(rs.getString("dob"));
				bean.setDpPath(rs.getString("path"));
				
				return bean;

			}
		}, new Object[] { 1, email });
		cartBean = fetchUserCart(userBean.getEmail());
		userBean.setCart(cartBean);
		}
		catch(Exception ex) {
		   System.out.println("ERROR in UserDao.fetchJwtUserDetails : \t"+ ex.getMessage());
		   throw new Exception(ex.getMessage());
		   
		}
		
		return userBean;

	}
	
	public List<ProductsBean> fetchAllProducts() throws Exception {
		List<ProductsBean> products= new LinkedList<>();
		try {
		String sql = SQLConstants.FETCH_ALL_PRODUCTS;
		products = this.jdbcTemplate.query(sql, new RowMapper<ProductsBean>() {
			public ProductsBean mapRow(ResultSet rs, int arg1) throws SQLException {
				ProductsBean bean = new ProductsBean();
				bean.setProductId(rs.getString("id"));
				bean.setProductName(rs.getString("name"));
				bean.setCategory(rs.getString("category"));
				bean.setPrice(rs.getString("price"));
				bean.setCreatedDttm(rs.getString("created_dttm"));
				bean.setUpdtDttm(rs.getString("updt_dttm"));
				bean.setPath(rs.getString("path"));
				bean.setStatus(rs.getString("status"));
				
				return bean;

			}
		});
		}
		catch(Exception ex) {
		   System.out.println("ERROR in UserDao.fetchAllProducts : \t"+ ex.getMessage());
		   throw new Exception(ex.getMessage());
		   
		}
		
		return products;

	}
	
	public String addToCart(int productId, String email) throws Exception {
		String msg="";
		try {
			String sql = SQLConstants.ADD_TO_CART;
			this.jdbcTemplate.update(sql, new Object[] {email,productId});
			msg="addedToCart";
		}
		catch(Exception ex) {
			   System.out.println("ERROR in UserDao.addToCart : \t"+ ex.getMessage());
			   throw new Exception(ex.getMessage());
			   
			}
		return msg;
	}
	
	public CartBean fetchUserCart(String email)throws Exception {
		List<ProductsBean> products= new LinkedList<>();
		try {
			String sql = SQLConstants.FETCH_USER_CART;
			products = this.jdbcTemplate.query(sql, new RowMapper<ProductsBean>() {
				public ProductsBean mapRow(ResultSet rs, int arg1) throws SQLException {
					ProductsBean bean = new ProductsBean();
					bean.setProductId(rs.getString("product_id"));
					bean.setProductName(rs.getString("product_name"));
					bean.setPrice(rs.getString("price"));
					bean.setCreatedDttm(rs.getString("added_on"));
					bean.setUpdtDttm(rs.getString("updated_on"));
					bean.setPath(rs.getString("path"));
					bean.setQty(rs.getInt("qty"));
					bean.setStatus(rs.getString("status"));
					
					return bean;

				}
			}, new Object[] {email });
			cartBean.setEmail(email);
			cartBean.setProducts(products);
			}
			catch(Exception ex) {
			   System.out.println("ERROR in UserDao.fetchUserCart : \t"+ ex.getMessage());
			   throw new Exception(ex.getMessage());
			   
			}
		return cartBean;
	}
	public String updateUserCart(String email,ProductsDto cart)throws Exception{
		String msg="";
		try {
			String sql=SQLConstants.UPDATE_USER_CART;
			this.jdbcTemplate.update(sql, new Object[] {cart.getQty(),cart.getStatus(),cart.getProductId(),email});
			msg="cartUpdated";
			
		}
		catch(Exception ex) {
			   System.out.println("ERROR in UserDao.updateUserCart : \t"+ ex.getMessage());
			   throw new Exception(ex.getMessage());
			   
			}
		return msg;
	}

}
