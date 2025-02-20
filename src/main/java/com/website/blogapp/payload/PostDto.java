package com.website.blogapp.payload;

import java.util.Date;

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
public class PostDto {
	private Integer postId;

	@NotBlank(message = "Title cannot be blank")
	@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
	private String postTitle;

	@NotBlank(message = "Content cannot be blank")
	@Size(min = 3, max = 5000, message = "Content must be between 3 and 5000 characters")
	private String postContent;

	@NotBlank(message = "Image name cannot be blank")
	private String postImageName;

	private Date postCreatedDate;
	
	private CategoryDto category;	// keep this field name: category, otherwise it'll  create mapping error
	
	private UserDto user;			// keep this field name: user, otherwise it'll create mapping error

}
