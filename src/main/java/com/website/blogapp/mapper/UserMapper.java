package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.User;
import com.website.blogapp.payload.UserDto;
import com.website.blogapp.payload.UserResponseDto;

@Component
public class UserMapper {

	@Autowired
	private ModelMapper userMapper;

	public User dtoToUser(UserDto userDto) {
		User user = userMapper.map(userDto, User.class);
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = userMapper.map(user, UserDto.class);
		return userDto;
	}

	public UserResponseDto userToResponseDto(User user) {
		UserResponseDto userResponseDto = userMapper.map(user, UserResponseDto.class);
		return userResponseDto;
	}
	
}
