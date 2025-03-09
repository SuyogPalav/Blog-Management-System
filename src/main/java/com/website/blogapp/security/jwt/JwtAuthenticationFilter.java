package com.website.blogapp.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.website.blogapp.service.impl.JwtServiceImpl;
import com.website.blogapp.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private UserDetailsServiceImpl customUserDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            final String authHeader = request.getHeader("Authorization");
            logger.info("Header: {}", authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwtToken = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwtToken);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(userEmail);
                Boolean validateToken = jwtService.isTokenValid(jwtToken, userDetails);

                if (validateToken) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.info("User Authenticated: {}", userEmail);
                } else {
                    logger.warn("Invalid JWT Token for user: {}", userEmail);
                }
            }

            filterChain.doFilter(request, response);

        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
            handleException(response, "Invalid JWT signature", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (MalformedJwtException ex) {
            logger.error("Malformed JWT token: {}", ex.getMessage());
            handleException(response, "Malformed JWT token", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (ExpiredJwtException ex) {
            logger.error("JWT token has expired: {}", ex.getMessage());
            handleException(response, "JWT token has expired", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (AuthenticationException ex) {
            logger.error("Authentication error: {}", ex.getMessage());
            handleException(response, "Authentication failed", HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception ex) {
            logger.error("JWT processing error: {}", ex.getMessage());
            handleException(response, "Internal server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
