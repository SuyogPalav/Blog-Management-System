package com.website.blogapp.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.website.blogapp.service.impl.JwtServiceImpl;
import com.website.blogapp.service.impl.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private UserDetailsServiceImpl customUserDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    final String authHeader = request.getHeader("Authorization");
	    logger.info("Header: {}", authHeader);

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    final String jwtToken = authHeader.substring(7);
	    final String userEmail = jwtService.extractUsername(jwtToken);

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (userEmail != null && authentication == null) {  // 🔄 FIXED: Changed condition
	        UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(userEmail);
	        Boolean validateToken = jwtService.isTokenValid(jwtToken, userDetails);

	        if (validateToken) {  // 🔄 FIXED: Removed `== true`
	            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            // ✅ Set Authentication in SecurityContext
	            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            logger.info("User Authenticated: {}", userEmail);
	        } else {
	            logger.warn("Invalid JWT Token for user: {}", userEmail);
	        }
	    }

	    filterChain.doFilter(request, response);
	}

}
