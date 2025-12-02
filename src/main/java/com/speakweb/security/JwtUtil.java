package com.speakweb.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	// Clave desde application.properties
	@Value("${jwt.secret}")
	private String secretKey;
	
	private Algorithm algorithm;
	
	@PostConstruct
	public void init() {
		this.algorithm = Algorithm.HMAC256(secretKey);
	}
	
	// Método create token
	public String create(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuer("Speakweb")
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
				.sign(algorithm);
	}

	// Método isValid el token
	public boolean isValid(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("Speakweb")
					.build();
			verifier.verify(token);
			return true;
		} catch (JWTVerificationException exception) {
			return false;
		}
	}
	
	// Método leer usuario del token
	public String getUserName(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("Speakweb")
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getSubject();
		} catch (JWTVerificationException exception) {
			return null;
		}
	}
	
}
