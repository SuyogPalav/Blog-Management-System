package com.website.blogapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;

public interface PostService {
	public PostContentResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	public PostDto getSinglePost(Integer postId);

	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId, MultipartFile postImageFile) throws IOException;

	public PostDto updatePost(Integer postId, PostDto postDto, MultipartFile postImageFile) throws IOException;

	public ApiResponse deletePost(Integer postId);

	public ApiResponse deleteAllPost();

	public List<PostDto> getPostByCateory(Integer cateoryId);

	public List<PostDto> getPostByUser(Integer userId);

	public List<PostDto> searchPostByTitle(String keywords);


}
