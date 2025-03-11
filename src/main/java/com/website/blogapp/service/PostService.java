package com.website.blogapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.payload.PostResponseDto;

import jakarta.servlet.http.HttpServletResponse;

public interface PostService {
	public PostContentResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	public PostResponseDto getSinglePost(Integer postId);

	public PostResponseDto createPost(PostDto postDto, String userEmail, Integer categoryId, MultipartFile postImageFile)
			throws IOException;

	public PostResponseDto updatePost(Integer postId, PostDto postDto, MultipartFile postImageFile) throws IOException;

	public ApiResponse deletePost(Integer postId);

	public ApiResponse deleteAllPost();

	public List<PostResponseDto> getPostByCategory(Integer categoryId);

	public List<PostResponseDto> getPostByUser(Integer userId);

	public List<PostResponseDto> searchPostByTitle(String keywords);

	public List<PostResponseDto> findPostByTitleAndCategory(Integer categoryId, String postTitle);

	public void exportPostInCsv(Integer userId, HttpServletResponse response) throws IOException;
}
