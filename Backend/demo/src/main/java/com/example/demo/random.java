package com.example.demo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

public class random {

	public static void main(String[] args) {
	    // Get the JWT token
	    String jwt = "eyJhbGciOiJub25lIn0.eyJuYW1lIjoiQXJwaXQgU2h1a2xhIiwiZW1haWwiOiJzaHVrbGFhcnBpdDAwMDA3QGdtYWlsLmNvbSIsInN1YiI6ImJsb29tIiwianRpIjoiYTI1MTQwNjktMTc2Yy00MTU1LWJjM2EtYjNkMmMzNzZkYjBmIiwiaWF0IjoxNjg1ODgwODMwLCJleHAiOjE2ODU4ODExMzB9.";

	    // Parse the JWT token
	    Jwt claims = Jwts.parser().parse(jwt);
	   Object obj= claims.getBody();
	   System.out.println(obj);

	   
	}
}
