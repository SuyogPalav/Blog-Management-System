package com.website.blogapp.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.website.blogapp.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	// Extracts the username (subject) from the JWT token.
	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}

	// Extracts a specific claim from the JWT token using a claims resolver
	// function.
	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}

	// Generates a JWT token with default extra claims.
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	// Generates a JWT token with additional claims provided in the extraClaims map.
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	// Returns the JWT expiration time.
	public long getExpirationTime() {
		return jwtExpiration;
	}

	// Builds the JWT token with claims, subject, issue date, expiration, and
	// signing key.
	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	// Validates the JWT token by checking username and expiration status.
	public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		final String username = extractUsername(jwtToken);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
	}

	// Checks if the JWT token is expired.
	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}

	// Extracts the expiration date from the JWT token.
	private Date extractExpiration(String jwtToken) {
		return extractClaim(jwtToken, Claims::getExpiration);
	}

	// Extracts all claims from the JWT token.
	private Claims extractAllClaims(String jwtToken) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken).getBody();
	}

	// Converts the secret key from a Base64-encoded string to a Key object for
	// signing JWTs.
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
