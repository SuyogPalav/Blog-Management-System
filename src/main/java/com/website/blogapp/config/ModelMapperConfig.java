package com.website.blogapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;

		/*
		// Ignore id when mapping from UserDto -> User
		modelMapper.addMappings(new PropertyMap<UserDto, User>() {
			@Override
			protected void configure() {
				skip(destination.getUserId());
			}
		});

		// Ignore id when mapping from CategoryDto -> Category
		modelMapper.addMappings(new PropertyMap<CategoryDto, Category>() {
			@Override
			protected void configure() {
				skip(destination.getCategoryId());
			}
		});

		// Ignore id when mapping from PostDto -> Post
		modelMapper.addMappings(new PropertyMap<PostDto, Post>() {
			@Override
			protected void configure() {
				skip(destination.getPostId());
			}
		});

		return modelMapper;
		*/
		
	}
}
