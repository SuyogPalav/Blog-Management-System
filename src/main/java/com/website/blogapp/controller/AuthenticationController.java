package com.website.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogapp.payload.LoginRequestDto;
import com.website.blogapp.payload.LoginResponseDto;
import com.website.blogapp.payload.UserDto;
import com.website.blogapp.payload.UserResponseDto;
import com.website.blogapp.service.AuthenticationService;
import com.website.blogapp.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth/")
@Tag(name = "Authentication Controller", description = "REST APIs related to perform Authentication operations!!")
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> registerNewUser(@RequestBody UserDto userDto) {
		UserResponseDto userResponseDto = userService.registerNewUser(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
		LoginResponseDto loginResponseDto = authenticationService.loginAuthentication(loginRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);

	}

}
