package com.example.demo.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.session.InMemoryWebSessionStore;
import org.springframework.web.server.session.WebSessionStore;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.ProductsDto;
import com.example.demo.model.ProductsBean;
import com.example.demo.model.UserBean;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTTokenCreator;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/")
public class HomeController {
	@Autowired
	JWTTokenCreator jwtTokenCreator;
	@Autowired
	UserService userService;
	@Autowired
	UserBean userBean;
	@Autowired
	ProductsBean productsBean;
	private Map<String, HttpSession> sessions = new HashMap<>();

    public HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
	
	/**
	 * Welcome page
	 * @return welcome text
	 */
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView welcome() {
		ModelAndView view = new ModelAndView();
		view.setViewName("index.html");
		return view;
		
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity login(@RequestBody LoginDto loggedInUser, HttpSession session) {
		//pwd encryption
		ResponseEntity responseEntity=null;
		try {
			userBean=userService.fetchLoggedInUserDetails(loggedInUser);
			if(userBean!=null)
			{
				
			//MultiValueMap<String, String>headersp = null;
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("jwt", jwtTokenCreator.jwtBuilder(userBean));
			session.setAttribute("userDetails", userBean);
			sessions.put(session.getId(),session);
			headers.add("session", session.getId());
			
			
			
			//headers.put("jwt", jwtTokenCreator.jwtBuilder(userBean));
			
			responseEntity=new ResponseEntity<UserBean>(userBean, headers, HttpStatus.OK);
			}
			else {
				String error="Unauthorised User \t:"+loggedInUser.getUsername();
				responseEntity=new ResponseEntity<String>(error,HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
					
		}
		
		
		return responseEntity;
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/sessionCheck", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity sessionCheck(@RequestHeader("jwt")String jwt) {
		ResponseEntity responseEntity=null;
		
		try {
			Jwt claims = Jwts.parser().parse(jwt);
			   Map<String,Object> mp =  (Map<String, Object>) claims.getBody();
			   
			   userBean=userService.fetchJwtUserDetails((String)mp.get("email"));
			   
		if(userBean==null)
		{
			responseEntity=new ResponseEntity<String>("loggedOut", HttpStatus.OK);
		}
		else {
			HttpHeaders headers = new HttpHeaders();
			headers.add("jwt", jwtTokenCreator.jwtBuilder(userBean));
			responseEntity=new ResponseEntity<UserBean>(userBean, headers, HttpStatus.OK);			
		}
	}
		catch (Exception e) {
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
					
		}
	return responseEntity;
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/sessionCheckOk", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity sessionCheckNew(@RequestHeader("session") String sessionId) {
		ResponseEntity responseEntity=null;
		
       

		try {
	    HttpSession session = sessions.get(sessionId);	   
		if(session.getAttribute("userDetails")==null)
		{
			responseEntity=new ResponseEntity<String>("loggedOut", HttpStatus.OK);
		}
		else {
			
			userBean=(UserBean) session.getAttribute("userDetails");
			userBean = userService.fetchJwtUserDetails(userBean.getEmail());
			HttpHeaders headers = new HttpHeaders();
			headers.add("jwt", jwtTokenCreator.jwtBuilder(userBean));
			responseEntity=new ResponseEntity<UserBean>(userBean, headers, HttpStatus.OK);			
		}
	}
		catch (Exception e) {
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
					
		}
	return responseEntity;
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/logout", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity logout(@RequestHeader("session") String sessionId) {
		ResponseEntity responseEntity=null;
		try {
			HttpSession session = sessions.get(sessionId);
			sessions.remove(session.getId());
			session.invalidate();
			responseEntity=new ResponseEntity<String>("loggedOut", HttpStatus.OK);
			
		}
		catch (Exception e) {
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
					
		}
		return responseEntity;
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/products", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity products() {
		ResponseEntity responseEntity=null;
		try {
			List<ProductsBean>products = userService.fetchAllProducts();
			responseEntity=new ResponseEntity<List>(products, HttpStatus.OK);
			
		}
		catch (Exception e) {
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
					
		}
		return responseEntity;
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/addToCart/{productId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity addToCart(@PathVariable("productId")String productId, @RequestHeader("session") String sessionId) {
		ResponseEntity responseEntity=null;
	
		try {
	    HttpSession session = sessions.get(sessionId);
	    userBean=(UserBean) session.getAttribute("userDetails");
	    String msg= userService.addToCart(userBean, productId);
	    Map<String,String> mp = new HashMap<>();
	    mp.put("msg", msg);
	    responseEntity=new ResponseEntity<Map>(mp, HttpStatus.OK);
	    
		}
		catch (Exception e) {
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
					
		}
		
		return responseEntity;
	}
	@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*",allowCredentials = "true", exposedHeaders = "*")
	@RequestMapping(value="/updateCart", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity updateCart(@RequestBody ProductsDto cart, @RequestHeader("session") String sessionId) {
		ResponseEntity responseEntity=null;
		try {
			HttpSession session = sessions.get(sessionId);
		    userBean=(UserBean) session.getAttribute("userDetails");
		    userBean=userService.updateUserCart(userBean.getEmail(), cart);
		    HttpHeaders headers = new HttpHeaders();
			headers.add("jwt", jwtTokenCreator.jwtBuilder(userBean));
			responseEntity=new ResponseEntity<UserBean>(userBean, headers, HttpStatus.OK);
			
		}
		catch(Exception e){
			String error="Some Error Occured \t:"+e.getMessage();
			responseEntity=new ResponseEntity<String>(error,HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	
}
