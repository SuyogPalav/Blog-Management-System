package com.website.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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
	
	@Schema(example = "Smart-Phones")
	@NotBlank(message = "Title cannot be blank")
	@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
	private String categoryTitle;
	
	@Schema(example = "You can find newly launched smart phones here")
	@NotBlank(message = "Description cannot be blank")
	private String categoryDesc;

}
