package com.website.blogapp.service.impl;

import java.util.List;
import java.util.Optional;
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
	public List<UserDto> getAllUser() {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		List<User> user = userRepository.findAll();
		List<UserDto> userDto = user.stream().map(userMapper::userToDto).toList();
		return userDto;

	}

	@Override
	public UserDto getSingleUser(Integer userId) {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		UserDto userDto = userRepository.findById(userId).map(userMapper::userToDto)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		return userDto;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = userMapper.dtoToUser(userDto);
		userRepository.save(user);
		UserDto userDtoCreated = userMapper.userToDto(user);
		return userDtoCreated;
	}

	@Override
	public UserDto updateUser(Integer userId, UserDto userDto) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		// Update existing user with new details
		existingUser.setUserName(userDto.getUserName());
		existingUser.setUserEmail(userDto.getUserEmail());
		existingUser.setUserPassword(userDto.getUserPassword());
		existingUser.setUserAbout(userDto.getUserAbout());

		userRepository.save(existingUser);
		UserDto userDtoUpdated = userMapper.userToDto(existingUser);
		return userDtoUpdated;

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
	public List<UserDto> searchUsersStartingWith(String userName) {
		List<User> users = userRepository.searchByUserName(userName);
		if (users.isEmpty()) {
			throw new UserNotFoundException("User " + userName + " does not exist.");
		}

		List<UserDto> userDto = users.stream().map((user) -> userMapper.userToDto(user)).collect(Collectors.toList());
		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = userMapper.dtoToUser(userDto);

		Optional<User> userData = userRepository.findByUserEmail(user.getUserEmail());

		if (userData.isPresent()) {
			throw new DuplicateUserEmailFoundException(
					"This email is already exist! Please Sign Up with another email!");
		} else {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setUserPassword(encodedPassword);
			Role role = roleRepository.findById(RoleConstant.ROLE_NORMAL).get();
			user.getRoles().add(role);
			userRepository.save(user);

			UserDto registeredUserDto = userMapper.userToDto(user);

			return registeredUserDto;
		}
	}

}
