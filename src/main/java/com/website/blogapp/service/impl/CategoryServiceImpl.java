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
		List<CategoryDto> categoryDtoPageContent = categoryPageContent.stream()
				.map((category) -> categoryMapper.categoryToDto(category)).collect(Collectors.toList());

		CategoryContentResponse categoryContentResponse = CategoryContentResponse.builder()
				.categoryDtoPageContent(categoryDtoPageContent).pageNumber(page.getNumber()).pageSize(page.getSize())
				.totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).lastPage(page.isLast())
				.build();

		return categoryContentResponse;

	}

	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
		if (categoryRepository.count() == 0) {
			throw new CategoryDatabaseIsEmptyException("No categories found in the database.");
		}

		return categoryRepository.findById(categoryId).map(categoryMapper::categoryToDto)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = categoryMapper.dtoToCategory(categoryDto);
		categoryRepository.save(category);
		CategoryDto categoryDtoCreated = categoryMapper.categoryToDto(category);
		return categoryDtoCreated;
	}

	@Override
	public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		existingCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		existingCategory.setCategoryDesc(categoryDto.getCategoryDesc());
		categoryRepository.save(existingCategory);

		CategoryDto categoryDtoUpdated = categoryMapper.categoryToDto(existingCategory);
		return categoryDtoUpdated;

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
	public List<CategoryDto> searchCategoryWithTitle(String categoryTitle) {
		List<Category> categories = categoryRepository.searchByCategoryTitle(categoryTitle);
		if (categories.isEmpty()) {
			throw new CategoryNotFoundException("Category " + categoryTitle + " does not exist.");
		}

		List<CategoryDto> categoryDto = categories.stream().map((category) -> categoryMapper.categoryToDto(category))
				.collect(Collectors.toList());
		return categoryDto;

	}

}
