package com.website.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogapp.constants.PostConstant;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;

	@GetMapping("/post/readAll")
	public ResponseEntity<PostContentResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = PostConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PostConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = PostConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = PostConstant.SORT_DIR, required = false) String sortDir) {

		PostContentResponse postContentResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.status(HttpStatus.OK).body(postContentResponse);
	}

	@GetMapping("/post/readSingle/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId") Integer postId) {
		PostDto postDto = postService.getSinglePost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);
	}

	@PostMapping("/create/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId, @Valid @RequestBody PostDto postDto) {
		PostDto postDtoCreated = postService.createPost(postDto, userId, categoryId);
		return ResponseEntity.status(HttpStatus.CREATED).body(postDtoCreated);

	}

	@PutMapping("/post/updateSingle/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Integer postId,
			@Valid @RequestBody PostDto postDto) {
		PostDto postDtoUpdated = postService.updatePost(postId, postDto);
		return ResponseEntity.status(HttpStatus.OK).body(postDtoUpdated);
	}

	@DeleteMapping("/post/deleteSingle/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		ApiResponse apiResponse = postService.deletePost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@DeleteMapping("/post/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllPost() {
		ApiResponse apiResponse = postService.deleteAllPost();
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/read/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId) {
		List<PostDto> postDto = postService.getPostByUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);

	}

	@GetMapping("/read/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> postDto = postService.getPostByCateory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);

	}

	@GetMapping("/post/searchByTitle/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> postDto = postService.searchPostByTitle(keywords);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);

	}

}
