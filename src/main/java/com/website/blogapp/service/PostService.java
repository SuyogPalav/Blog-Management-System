package com.website.blogapp.service;

import java.util.List;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;

public interface PostService {
	public PostContentResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	public PostDto getSinglePost(Integer postId);

	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	public PostDto updatePost(Integer postId, PostDto postDto);

	public ApiResponse deletePost(Integer postId);

	public ApiResponse deleteAllPost();

	// Add 3 new methods
	public List<PostDto> getPostByCateory(Integer cateoryId);

	public List<PostDto> getPostByUser(Integer userId);

	public List<PostDto> searchPostByTitle(String keywords);


}
