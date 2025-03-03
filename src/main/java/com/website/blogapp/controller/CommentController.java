package com.website.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CommentDto;
import com.website.blogapp.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/create/user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId, @Valid @RequestBody CommentDto commentDto) {
		CommentDto commentDtoCreated = commentService.createComment(commentDto, userId, postId);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoCreated);

	}

	@DeleteMapping("/comment/deleteSingle/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {
		ApiResponse apiResponse = commentService.deleteComment(commentId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}

}
