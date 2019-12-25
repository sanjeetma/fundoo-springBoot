package com.bridgelabz.fundo.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JWTUtility {

	private final long EXPIRATION_TIME = 60 * 5*1000;
	private final String SECRET = "bridgelabz";

	public String generateToken(Long id) {
		String token = null;
		try {
			token = JWT.create().withClaim("userId", id)
//					.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					.sign(Algorithm.HMAC512(SECRET));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token; 
	}

	public Long verify(String token) {
		Long id = null;
		if (token != null) {
			id = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token).getClaim("userId").asLong();
		}
		 return id;
	}
}
