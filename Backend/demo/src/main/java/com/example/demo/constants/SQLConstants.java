package com.example.demo.constants;

import com.fasterxml.jackson.core.StreamReadConstraints.Builder;

public class SQLConstants {
	

	public static String FETCH_LOGGED_IN_USER_DETAILS = new StringBuilder("select user.name,user.email,user.phone,user.dob,user.gender,role.role, ")
			.append("(select img_path from bloom.images where img_type_id=? and source_id=user.id) as path ")
			.append("from bloom.user as user join bloom.roles as role on role.id=user.role_id  where username = ? and PASSWORD=?")
			.toString();
	
	public static String FETCH_JWT_USER_DETAILS = new StringBuilder("select user.name,user.email,user.phone,user.dob,user.gender,role.role, ")
			.append("(select img_path from bloom.images where img_type_id=? and source_id=user.id) as path ")
			.append("from bloom.user as user join bloom.roles as role on role.id=user.role_id  where user.email=?")
			.toString();
	
	public static String FETCH_ALL_PRODUCTS = new StringBuilder("select prd.id, prd.name, prd.price, prd.status, cat.category, ") 
			  .append("prd.created_dttm, prd.updt_dttm, (select img_path from bloom.images where img_type_id = 2 and source_id = prd.id) as path ") 
			.append("from bloom.products as prd join bloom.categories cat on prd.category_id = cat.id").toString();
	
	public static String ADD_TO_CART = new StringBuilder("INSERT INTO bloom.cart ( ")
			  .append("user_id, product_id, qty, status, ") 
			  .append("created_dttm) VALUES ((select id from bloom.user ") 
			      .append("where email = ?), ?, 1,'ACTIVE',now())").toString();
	
	public static String FETCH_USER_CART = new StringBuilder("SELECT crt.id as cart_id, crt.user_id as user_id, crt.status as status, ")
			.append("prd.id as product_id , prd.name as product_name, ")
			.append("prd.price as price, crt.created_dttm as added_on, ")
			.append("crt.updt_dttm as updated_on, ") 
			.append("(select img_path from bloom.images where img_type_id=2 and source_id=prd.id ) as path, ")
			.append("crt.qty as qty FROM BLOOM.PRODUCTS PRD JOIN BLOOM.CART CRT ") 
			.append("on crt.product_id=prd.id where crt.user_id= ")
			.append("(select id from bloom.user where email=?) and crt.status='ACTIVE' order by added_on desc").toString();
	
	public static String UPDATE_USER_CART = new StringBuilder("Update bloom.cart set qty=?,status=?,updt_dttm=now() ")
			.append("where product_id=? and user_id=(Select id from bloom.user ") 
			.append("where email=?)").toString();

	
	
}
