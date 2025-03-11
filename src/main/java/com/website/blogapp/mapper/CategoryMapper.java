package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.Category;
import com.website.blogapp.payload.CategoryDto;
import com.website.blogapp.payload.CategoryResponseDto;

@Component
public class CategoryMapper {
	@Autowired
	private ModelMapper categoryMapper;

	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category = categoryMapper.map(categoryDto, Category.class);
		return category;
	}

	public CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = categoryMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
	public CategoryResponseDto categoryToResponseDto(Category category) {
		CategoryResponseDto categoryResponseDto = categoryMapper.map(category, CategoryResponseDto.class);
		return categoryResponseDto;
	}

}
