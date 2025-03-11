package com.website.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	@Schema(example = "Awesome Article Suyog!")
	@NotBlank(message = "Content cannot be blank")
	@Size(max = 200, message = "Content must be in 200 characters")
	private String commentContent;
}
