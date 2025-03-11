package com.website.blogapp.service;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto, String userEmail, Integer postId);

	public ApiResponse deleteComment(Integer commentId);

}
