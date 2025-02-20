package com.website.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.website.blogapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/readAll")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> userDtoAll = userService.getAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(userDtoAll);
	}

	@GetMapping("/readSingle/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
		UserDto userDto = userService.getSingleUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto userDtoCreated = userService.createUser(userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCreated);
	}

	@PutMapping("/updateSingle/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Integer userId,
			@Valid @RequestBody UserDto userDto) {
		UserDto userDtoUpdated = userService.updateUser(userId, userDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDtoUpdated);
	}

	@DeleteMapping("/deleteSingle/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		ApiResponse apiResponse = userService.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllUser() {
		ApiResponse apiResponse = userService.deleteAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/searchByName/{userName}")
	public ResponseEntity<List<UserDto>> searchUsersStartingWith(@PathVariable("userName") String userName) {
		List<UserDto> userDto = userService.searchUsersStartingWith(userName);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
}
