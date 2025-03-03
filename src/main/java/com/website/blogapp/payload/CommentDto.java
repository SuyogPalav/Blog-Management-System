package com.website.blogapp.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private Integer commentId;
	
	@NotBlank(message = "Content cannot be blank")
	@Size(max = 200, message = "Content must be in 200 characters")
	private String commentContent;

}
