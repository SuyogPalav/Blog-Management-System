package com.website.blogapp.service;

import com.website.blogapp.payload.LoginRequestDto;
import com.website.blogapp.payload.LoginResponseDto;
import com.website.blogapp.payload.UserDto;

public interface AuthenticationService {

	public UserDto signup(UserDto userDto);

	public LoginResponseDto loginAuthentication(LoginRequestDto loginRequestDto);

}
