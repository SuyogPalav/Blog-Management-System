package com.website.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CommentDto;
import com.website.blogapp.service.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment Controller", description = "REST APIs related to perform Comment operations!!")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/create/user/{userId}/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId, @Valid @RequestBody CommentDto commentDto) {
		CommentDto commentDtoCreated = commentService.createComment(commentDto, userId, postId);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoCreated);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteSingle/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {
		ApiResponse apiResponse = commentService.deleteComment(commentId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}

}
