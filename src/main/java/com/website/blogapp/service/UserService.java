package com.website.blogapp.service;

import java.util.List;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.UserDto;

public interface UserService {

	public List<UserDto> getAllUser();

	public UserDto getSingleUser(Integer userId);

	public UserDto createUser(UserDto userDto);

	public UserDto updateUser(Integer userId, UserDto userDto);

	public ApiResponse deleteUser(Integer userId);

	public ApiResponse deleteAllUser();
	
	public List<UserDto> searchUsersStartingWith(String userName);

}
