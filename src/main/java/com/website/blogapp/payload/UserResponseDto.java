package com.website.blogapp.payload;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
	
	private String userId;
	private String userName;
	private String userEmail;
	private String userAbout;
	private Set<RoleDto> roles = new HashSet<>();
	
}
