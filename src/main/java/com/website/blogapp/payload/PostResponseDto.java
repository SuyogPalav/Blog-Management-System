package com.website.blogapp.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postImageName;
	private Date postCreatedDate;
	private CategoryDto category;
	private UserResponseDto user;
	private List<CommentResponseDto> comment = new ArrayList<>();

}
