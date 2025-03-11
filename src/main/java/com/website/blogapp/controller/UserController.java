package com.website.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.UserDto;
import com.website.blogapp.payload.UserResponseDto;
import com.website.blogapp.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/")
@Tag(name = "User Controller", description = "REST APIs related to perform User operations!!")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/readAll")
	public ResponseEntity<List<UserResponseDto>> getAllUser() {
		List<UserResponseDto> userResponseDto = userService.getAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
	}

	@GetMapping("/readSingle/{userId}")
	public ResponseEntity<UserResponseDto> getSingleUser(@PathVariable("userId") Integer userId) {
		UserResponseDto userResponseDto = userService.getSingleUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
	}

	@PostMapping("/create")
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserResponseDto userResponseDto = userService.createUser(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
	}

	@PutMapping("/updateSingle/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userId") Integer userId,
			@Valid @RequestBody UserDto userDto) {
		UserResponseDto userResponseDto = userService.updateUser(userId, userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteSingle/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		ApiResponse apiResponse = userService.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllUser() {
		ApiResponse apiResponse = userService.deleteAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/searchByName/{userName}")
	public ResponseEntity<List<UserResponseDto>> searchUsersStartingWith(@PathVariable("userName") String userName) {
		List<UserResponseDto> userResponseDto = userService.searchUsersStartingWith(userName);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
	}
}
