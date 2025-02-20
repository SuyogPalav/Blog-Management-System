package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.Category;
import com.website.blogapp.payload.CategoryDto;

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

}
