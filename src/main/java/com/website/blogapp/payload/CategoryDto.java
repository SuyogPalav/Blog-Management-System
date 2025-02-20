package com.website.blogapp.payload;

import jakarta.validation.constraints.NotBlank;
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
public class CategoryDto {
	private Integer categoryId;

	@NotBlank(message = "Title cannot be blank")
	@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
	private String categoryTitle;

	@NotBlank(message = "Description cannot be blank")
	private String categoryDesc;

}
