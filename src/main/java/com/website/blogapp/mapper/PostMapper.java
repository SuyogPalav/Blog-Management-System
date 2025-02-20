package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.Post;
import com.website.blogapp.payload.PostDto;

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
}
