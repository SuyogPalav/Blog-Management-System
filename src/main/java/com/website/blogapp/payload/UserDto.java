package com.website.blogapp.payload;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
	private Integer userId;

	@NotBlank(message = "Name cannot be blank")
	@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
	private String userName;

	@NotBlank(message = "Email cannot be blank")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
	private String userEmail;

	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, message = "Password must be at least 6 characters")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String userPassword;

	@NotBlank(message = "About cannot be blank")
	private String userAbout;
	
	private Set<RoleDto> roles = new HashSet<>();

}
