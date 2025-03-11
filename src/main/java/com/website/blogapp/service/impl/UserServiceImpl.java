package com.website.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.website.blogapp.constants.RoleConstant;
import com.website.blogapp.constants.UserConstant;
import com.website.blogapp.entity.Role;
import com.website.blogapp.entity.User;
import com.website.blogapp.exception.DuplicateUserEmailFoundException;
import com.website.blogapp.exception.UserDatabaseIsEmptyException;
import com.website.blogapp.exception.UserNotFoundException;
import com.website.blogapp.mapper.UserMapper;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.UserDto;
import com.website.blogapp.payload.UserResponseDto;
import com.website.blogapp.repository.RoleRepository;
import com.website.blogapp.repository.UserRepository;
import com.website.blogapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<UserResponseDto> getAllUser() {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		List<User> user = userRepository.findAll();
		List<UserResponseDto> userDto = user.stream().map(userMapper::userToResponseDto).toList();
		return userDto;

	}

	@Override
	public UserResponseDto getSingleUser(Integer userId) {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		UserResponseDto userResponseDto = userRepository.findById(userId).map(userMapper::userToResponseDto)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		return userResponseDto;
	}

	@Override
	public UserResponseDto createUser(UserDto userDto) {
		User user = userMapper.dtoToUser(userDto);
		userRepository.save(user);
		UserResponseDto userResponseDto = userMapper.userToResponseDto(user);
		return userResponseDto;
	}

	@Override
	public UserResponseDto updateUser(Integer userId, UserDto userDto) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		// Update existing user with new details
		existingUser.setUserName(userDto.getUserName());
		existingUser.setUserEmail(userDto.getUserEmail());
		existingUser.setUserPassword(userDto.getUserPassword());
		existingUser.setUserAbout(userDto.getUserAbout());

		userRepository.save(existingUser);
		UserResponseDto userResponseDto = userMapper.userToResponseDto(existingUser);
		return userResponseDto;
	}

	@Override
	public ApiResponse deleteUser(Integer userId) {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		userRepository.deleteById(userId);
		ApiResponse apiResponse = ApiResponse.builder().message("User " + userId + " has been successfully deleted")
				.success(UserConstant.SUCCESS).build();
		return apiResponse;
	}

	@Override
	public ApiResponse deleteAllUser() {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		userRepository.deleteAll();
		ApiResponse apiResponse = ApiResponse.builder().message("All users has been succssfully deleted")
				.success(UserConstant.SUCCESS).build();
		return apiResponse;
	}

	@Override
	public List<UserResponseDto> searchUsersStartingWith(String userName) {
		List<User> users = userRepository.searchByUserName(userName);
		if (users.isEmpty()) {
			throw new UserNotFoundException("User " + userName + " does not exist.");
		}

		List<UserResponseDto> userResponseDto = users.stream().map((user) -> userMapper.userToResponseDto(user)).collect(Collectors.toList());
		return userResponseDto;

	}

	@Override
	public UserResponseDto registerNewUser(UserDto userDto) {
		User user = userMapper.dtoToUser(userDto);

		User userData = userRepository.findByUserEmail(user.getUserEmail());

		if (userData!=null) {
			throw new DuplicateUserEmailFoundException(
					"This email is already exist! Please Sign Up with another email!");
		} else {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setUserPassword(encodedPassword);
			Role role = roleRepository.findById(RoleConstant.ROLE_NORMAL).get();
			user.getRoles().add(role);
			userRepository.save(user);

			UserResponseDto userResponseDto = userMapper.userToResponseDto(user);

			return userResponseDto;
		}
	}

}
