package com.website.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseDto {

	private String userEmail;
	private String jwtToken;
	private Long jwtTokenExpiresIn;

}
