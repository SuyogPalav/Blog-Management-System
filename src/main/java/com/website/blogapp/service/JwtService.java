package com.website.blogapp.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String extractUsername(String jwtToken);

	<T> T extractClaim(String jwtToken, java.util.function.Function<io.jsonwebtoken.Claims, T> claimsResolver);

	String generateToken(UserDetails userDetails);

	String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

	long getExpirationTime();

	boolean isTokenValid(String jwtToken, UserDetails userDetails);
}
