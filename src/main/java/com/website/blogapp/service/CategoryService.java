package com.website.blogapp.service;

import java.util.List;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CategoryContentResponse;
import com.website.blogapp.payload.CategoryDto;
import com.website.blogapp.payload.CategoryResponseDto;

public interface CategoryService {
	public CategoryContentResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	public CategoryResponseDto getSingleCategory(Integer categoryId);

	public CategoryResponseDto createCategory(CategoryDto categoryDto);

	public CategoryResponseDto updateCategory(Integer categoryId, CategoryDto categoryDto);

	public ApiResponse deleteCategory(Integer categoryId);

	public ApiResponse deleteAllCategory();
	
	public List<CategoryResponseDto> searchCategoryWithTitle(String categoryTitle);

}
