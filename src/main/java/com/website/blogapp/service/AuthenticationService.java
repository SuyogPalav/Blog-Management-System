package com.website.blogapp.service;

import com.website.blogapp.payload.LoginRequestDto;
import com.website.blogapp.payload.LoginResponseDto;

public interface AuthenticationService {

	public LoginResponseDto loginAuthentication(LoginRequestDto loginRequestDto);

}
