package com.website.blogapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
	
	private Integer commentId;
	private String commentContent;
	private UserResponseDto user;	
}
