package com.website.blogapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.website.blogapp.entity.User;
import com.website.blogapp.exception.UserDatabaseIsEmptyException;
import com.website.blogapp.exception.UserNotFoundException;
import com.website.blogapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		User user = userRepository.findByUserEmail(userEmail);

		if (user == null) {
			throw new UserNotFoundException("User " + userEmail + " does not exist! Please Sign up for account!");
		}

//		User user = userRepository.findByUserEmail(userEmail)
//				.orElseThrow(() -> new UserNotFoundException("User " + userEmail + " does not exist! Please Sign up for account!"));

		return user;

	}
}
