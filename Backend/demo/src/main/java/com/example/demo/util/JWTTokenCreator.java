package com.example.demo.util;

import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;

import com.example.demo.dto.LoginDto;
import com.example.demo.model.UserBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JWTTokenCreator {
	public String jwtBuilder(UserBean user) {
		SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[256];
        random.nextBytes(bytes);
        Key key =  new SecretKeySpec(bytes, "HmacSHA256");
		String jwtToken = Jwts.builder()
		        .claim("name", user.getName())
		        .claim("email", user.getEmail())
		        .setSubject("bloom")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(Instant.now()))
		        .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
		        .compact();
		return jwtToken;
	}
	

}
