package com.website.blogapp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.website.blogapp.constants.PostConstant;
import com.website.blogapp.mapper.PostMapper;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.payload.PostResponseDto;
import com.website.blogapp.service.FileService;
import com.website.blogapp.service.PostService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
@Tag(name = "Post Controller", description = "REST APIs related to perform Post operations!!")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	PostMapper postMapper;

	@Value("${project.image}")
	private String path;

	@GetMapping("/readAll")
	public ResponseEntity<PostContentResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = PostConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PostConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = PostConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = PostConstant.SORT_DIR, required = false) String sortDir) {

		PostContentResponse postContentResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.status(HttpStatus.OK).body(postContentResponse);
	}

	@GetMapping("/readSingle/{postId}")
	public ResponseEntity<PostResponseDto> getSinglePost(@PathVariable("postId") Integer postId) {
		PostResponseDto postResponseDto = postService.getSinglePost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
	}

	@PostMapping(value = "/create/category/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostResponseDto> createPost(@PathVariable("categoryId") Integer categoryId,
			@Parameter(description = "{ \"postTitle\": \"String\", \"postContent\": \"String\" }") @RequestPart("postDto") @Valid String postDto,
			@Parameter(description = "Image file for the post (optional)") @RequestParam(value = "postImageFile", required = false) MultipartFile postImageFile,
			Principal principal) throws IOException {
		// Converting String into JSON
		PostDto postDtoInJson = objectMapper.readValue(postDto, PostDto.class);
		String userEmail = principal.getName();
		PostResponseDto postResponseDto = postService.createPost(postDtoInJson, userEmail, categoryId, postImageFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
	}

	@PutMapping(value = "/updateSingle/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostResponseDto> updatePost(@PathVariable("postId") Integer postId,
			@Parameter(description = "{ \"postTitle\": \"String\", \"postContent\": \"String\" }") @RequestPart("postDto") @Valid String postDto,
			@Parameter(description = "Image file for the post (optional)") @RequestParam(value = "postImageFile", required = false) MultipartFile postImageFile)
			throws IOException {
		PostDto postDtoInJson = objectMapper.readValue(postDto, PostDto.class);
		PostResponseDto postResponseDto = postService.updatePost(postId, postDtoInJson, postImageFile);
		return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteSingle/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		ApiResponse apiResponse = postService.deletePost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllPost() {
		ApiResponse apiResponse = postService.deleteAllPost();
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/read/user/{userId}")
	public ResponseEntity<List<PostResponseDto>> getPostByUser(@PathVariable("userId") Integer userId) {
		List<PostResponseDto> postResponseDto = postService.getPostByUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);

	}

	@GetMapping("/read/category/{categoryId}")
	public ResponseEntity<List<PostResponseDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostResponseDto> postResponseDto = postService.getPostByCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);

	}

	@GetMapping("/searchByTitle/{keywords}")
	public ResponseEntity<List<PostResponseDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostResponseDto> postResponseDto = postService.searchPostByTitle(keywords);
		return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);

	}

	// Image Upload
	@PostMapping(value = "/image/upload/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostResponseDto> uploadImage(@PathVariable("postId") Integer postId,
			@Parameter(description = "Image file for the post (optional)") @RequestParam(value = "postImageFile", required = true) MultipartFile postImageFile

	) throws IOException {

		PostResponseDto postResponseDto = postService.getSinglePost(postId);
		PostDto postDto = postMapper.postDtoToPostResponseDto(postResponseDto);

		String randomImageFileName = fileService.uploadImage(path, postImageFile);
		postDto.setPostImageName(randomImageFileName);

		PostResponseDto postResponseDtoUpdated = postService.updatePost(postId, postDto, postImageFile);

		return ResponseEntity.status(HttpStatus.OK).body(postResponseDtoUpdated);
	}

	// Image Download
	@GetMapping(value = "/image/download/{postImageFile}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("postImageFile") String postImageFile, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		InputStream inputStream = fileService.downloadImage(path, postImageFile);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());

	}

	@GetMapping("/category/{categoryId}/postTitle/{postTitle}")
	public ResponseEntity<List<PostResponseDto>> findPostByTitleAndCategory(
			@PathVariable("categoryId") Integer categoryId, @PathVariable("postTitle") String postTitle) {
		List<PostResponseDto> postResponseDto = postService.findPostByTitleAndCategory(categoryId, postTitle);
		return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
	}

	@GetMapping("/download/csv/user/{userId}")
	public void exportPostInCsv(@PathVariable("userId") Integer userId, HttpServletResponse response)
			throws IOException {
		postService.exportPostInCsv(userId, response);

	}

}
