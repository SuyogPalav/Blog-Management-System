package com.website.blogapp.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.website.blogapp.payload.LoginRequestDto;
import com.website.blogapp.payload.LoginResponseDto;
import com.website.blogapp.payload.UserDto;
import com.website.blogapp.service.AuthenticationService;
import com.website.blogapp.service.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@Override
	public UserDto signup(UserDto userDto) {

		return null;
	}

	@Override
	public LoginResponseDto loginAuthentication(LoginRequestDto loginRequestDto) {
		String userEmail = loginRequestDto.getUserEmail();
		String userPassword = loginRequestDto.getUserPassword();

		System.out.println(userEmail);
		System.out.println(userPassword);

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userEmail, userPassword);
		authenticationManager.authenticate(usernamePasswordAuthenticationToken); // BadCreadentialException
		UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
		System.out.println(userDetails);

		String jwtToken = jwtService.generateToken(userDetails);
		Long jwtExpirationTime = jwtService.getExpirationTime();
		Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		LoginResponseDto loginResponseDto = LoginResponseDto.builder().userEmail(userEmail).jwtToken(jwtToken)
				.jwtTokenExpiresIn(jwtExpirationTime).roles(roles).build();

		return loginResponseDto;

	}
}
