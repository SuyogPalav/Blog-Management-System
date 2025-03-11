package com.website.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequestDto {

	@Schema(example = "suyog@gmail.com")
	private String userEmail;

	@Schema(example = "pass@123")
	private String userPassword;

}
