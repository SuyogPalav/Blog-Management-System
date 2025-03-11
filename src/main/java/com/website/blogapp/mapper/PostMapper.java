package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.Post;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.payload.PostResponseDto;

@Component
public class PostMapper {
	@Autowired
	private ModelMapper postMapper;

	public Post dtoToPost(PostDto postDto) {
		Post post = postMapper.map(postDto, Post.class);
		return post;
	}

	public PostDto postToDto(Post post) {
		PostDto postDto = postMapper.map(post, PostDto.class);
		return postDto;
	}
	
	public PostResponseDto postToResponseDto(Post post) {
		PostResponseDto postResponseDto = postMapper.map(post, PostResponseDto.class);
		return postResponseDto;
	}
	
	// For Image upload
	public PostDto postDtoToPostResponseDto(PostResponseDto postResponseDto) {
		PostDto postDto = postMapper.map(postResponseDto, PostDto.class);
		return postDto;
	}
}
