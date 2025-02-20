package com.website.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogapp.constants.CategoryConstant;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CategoryContentResponse;
import com.website.blogapp.payload.CategoryDto;
import com.website.blogapp.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/readAll")
	public ResponseEntity<CategoryContentResponse> getAllCategory(
			@RequestParam(value = "pageNumber", defaultValue = CategoryConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = CategoryConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = CategoryConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = CategoryConstant.SORT_DIR, required = false) String sortDir) {

		CategoryContentResponse categoryContentResponse = categoryService.getAllCategory(pageNumber, pageSize, sortBy,
				sortDir);
		return ResponseEntity.status(HttpStatus.OK).body(categoryContentResponse);
	}

	@GetMapping("/readSingle/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("categoryId") Integer categoryId) {
		CategoryDto categoryDto = categoryService.getSingleCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
	}

	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto categoryDtoCreated = categoryService.createCategory(categoryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoCreated);

	}

	@PutMapping("/updateSingle/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Integer categoryId,
			@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto categoryDtoUpdated = categoryService.updateCategory(categoryId, categoryDto);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDtoUpdated);

	}

	@DeleteMapping("/deleteSingle/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		ApiResponse apiResponse = categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllCategory() {
		ApiResponse apiResponse = categoryService.deleteAllCategory();
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/searchByTitle/{categoryTitle}")
	public ResponseEntity<List<CategoryDto>> searchCategoryWithTitle(
			@PathVariable("categoryTitle") String categoryTitle) {
		List<CategoryDto> categoryDto = categoryService.searchCategoryWithTitle(categoryTitle);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
	}

}
