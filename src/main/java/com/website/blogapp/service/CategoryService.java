package com.website.blogapp.service;

import java.util.List;

import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CategoryContentResponse;
import com.website.blogapp.payload.CategoryDto;

public interface CategoryService {
	public CategoryContentResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	public CategoryDto getSingleCategory(Integer categoryId);

	public CategoryDto createCategory(CategoryDto categoryDto);

	public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);

	public ApiResponse deleteCategory(Integer categoryId);

	public ApiResponse deleteAllCategory();
	
	public List<CategoryDto> searchCategoryWithTitle(String categoryTitle);

}
