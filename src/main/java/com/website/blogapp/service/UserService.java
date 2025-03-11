package com.website.blogapp.service;

import java.util.List;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.UserDto;
import com.website.blogapp.payload.UserResponseDto;

public interface UserService {

	public UserResponseDto registerNewUser(UserDto userDto);

	public List<UserResponseDto> getAllUser();

	public UserResponseDto getSingleUser(Integer userId);

	public UserResponseDto createUser(UserDto userDto);

	public UserResponseDto updateUser(Integer userId, UserDto userDto);

	public ApiResponse deleteUser(Integer userId);

	public ApiResponse deleteAllUser();

	public List<UserResponseDto> searchUsersStartingWith(String userName);

}
