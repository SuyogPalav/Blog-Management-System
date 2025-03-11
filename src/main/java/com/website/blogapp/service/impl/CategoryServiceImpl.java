package com.website.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.website.blogapp.constants.CategoryConstant;
import com.website.blogapp.entity.Category;
import com.website.blogapp.exception.CategoryDatabaseIsEmptyException;
import com.website.blogapp.exception.CategoryNotFoundException;
import com.website.blogapp.mapper.CategoryMapper;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CategoryContentResponse;
import com.website.blogapp.payload.CategoryDto;
import com.website.blogapp.payload.CategoryResponseDto;
import com.website.blogapp.repository.CategoryRepository;
import com.website.blogapp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public CategoryContentResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		if (categoryRepository.count() == 0) {
			throw new CategoryDatabaseIsEmptyException("No categories found in the database.");
		}

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> page = categoryRepository.findAll(pageable);
		List<Category> categoryPageContent = page.getContent();
		List<CategoryResponseDto> categoryResponseDto = categoryPageContent.stream()
				.map((category) -> categoryMapper.categoryToResponseDto(category)).collect(Collectors.toList());

		CategoryContentResponse categoryContentResponse = CategoryContentResponse.builder()
				.categoryDtoPageContent(categoryResponseDto).pageNumber(page.getNumber()).pageSize(page.getSize())
				.totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).lastPage(page.isLast())
				.build();

		return categoryContentResponse;

	}

	@Override
	public CategoryResponseDto getSingleCategory(Integer categoryId) {
		if (categoryRepository.count() == 0) {
			throw new CategoryDatabaseIsEmptyException("No categories found in the database.");
		}
		CategoryResponseDto categoryResponseDto =  categoryRepository.findById(categoryId).map(categoryMapper::categoryToResponseDto)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		return categoryResponseDto;
	}

	@Override
	public CategoryResponseDto createCategory(CategoryDto categoryDto) {
		Category category = categoryMapper.dtoToCategory(categoryDto);
		categoryRepository.save(category);
		CategoryResponseDto categoryResponseDto = categoryMapper.categoryToResponseDto(category);
		return categoryResponseDto;
	}

	@Override
	public CategoryResponseDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		existingCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		existingCategory.setCategoryDesc(categoryDto.getCategoryDesc());
		categoryRepository.save(existingCategory);

		CategoryResponseDto categoryResponseDto = categoryMapper.categoryToResponseDto(existingCategory);
		return categoryResponseDto;

	}

	@Override
	public ApiResponse deleteCategory(Integer categoryId) {
		if (categoryRepository.count() == 0) {
			throw new CategoryDatabaseIsEmptyException("No categories found in the database.");
		}

		categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		categoryRepository.deleteById(categoryId);

		ApiResponse apiResponse = ApiResponse.builder()
				.message("Category " + categoryId + " has been successfully deleted").success(CategoryConstant.SUCCESS)
				.build();

		return apiResponse;
	}

	@Override
	public ApiResponse deleteAllCategory() {
		if (categoryRepository.count() == 0) {
			throw new CategoryDatabaseIsEmptyException("No categories found in the database.");
		}

		categoryRepository.deleteAll();

		ApiResponse apiResponse = ApiResponse.builder().message("All categories has been successfully deleted")
				.success(CategoryConstant.SUCCESS).build();

		return apiResponse;
	}

	@Override
	public List<CategoryResponseDto> searchCategoryWithTitle(String categoryTitle) {
		List<Category> categories = categoryRepository.searchByCategoryTitle(categoryTitle);
		if (categories.isEmpty()) {
			throw new CategoryNotFoundException("Category " + categoryTitle + " does not exist.");
		}

		List<CategoryResponseDto> categoryResponseDto = categories.stream().map((category) -> categoryMapper.categoryToResponseDto(category))
				.collect(Collectors.toList());
		return categoryResponseDto;

	}

}
