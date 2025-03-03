package com.website.blogapp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.constants.PostConstant;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.service.FileService;
import com.website.blogapp.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

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

	@PostMapping(value = "/create/user/{userId}/category/{categoryId}/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostDto> createPost(@PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId,
			@RequestPart("postDto") @Valid @ModelAttribute PostDto postDto,
			@RequestPart("postImageFile") MultipartFile postImageFile) throws IOException {
		PostDto postDtoCreated = postService.createPost(postDto, userId, categoryId, postImageFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(postDtoCreated);
	}

	@PutMapping("/post/updateSingle/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Integer postId,
			@RequestPart("postDto") @Valid @ModelAttribute PostDto postDto,
			@RequestPart("postImageFile") MultipartFile postImageFile) throws IOException {
		PostDto postDtoUpdated = postService.updatePost(postId, postDto, postImageFile);
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
		List<PostDto> postDto = postService.getPostByCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);

	}

	@GetMapping("/post/searchByTitle/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> postDto = postService.searchPostByTitle(keywords);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);

	}

	// Image Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam(value = "postImageFile", required = true) MultipartFile postImageFile,
			@PathVariable("postId") Integer postId) throws IOException {

		PostDto postDto = postService.getSinglePost(postId);
		String randomImageFileName = fileService.uploadImage(path, postImageFile);
		postDto.setPostImageName(randomImageFileName);
		PostDto postDtoUpdated = postService.updatePost(postId, postDto, postImageFile);

		return ResponseEntity.status(HttpStatus.OK).body(postDtoUpdated);
	}

	// Image Download
	@GetMapping(value = "/post/image/download/{postImageFile}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("postImageFile") String postImageFile, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		InputStream inputStream = fileService.downloadImage(path, postImageFile);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());

	}

	@GetMapping("/post/category/{categoryId}/postTitle/{postTitle}")
	public ResponseEntity<List<PostDto>> findPostByTitleAndCategory(@PathVariable("categoryId") Integer categoryId, 
			@PathVariable("postTitle") String postTitle) {
		List<PostDto> postDto = postService.findPostByTitleAndCategory(categoryId, postTitle);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);
	}
	



}
