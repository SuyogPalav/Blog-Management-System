package com.website.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.website.blogapp.payload.CategoryResponseDto;
import com.website.blogapp.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category/")
@Tag(name = "Category Controller", description = "REST APIs related to perform Category operations!!")
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
	public ResponseEntity<CategoryResponseDto> getSingleCategory(@PathVariable("categoryId") Integer categoryId) {
		CategoryResponseDto categoryResponseDto = categoryService.getSingleCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDto);
	}

	@PostMapping("/create")
	public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);

	}

	@PutMapping("/updateSingle/{categoryId}")
	public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable("categoryId") Integer categoryId,
			@Valid @RequestBody CategoryDto categoryDto) {
		CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId, categoryDto);
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDto);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteSingle/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		ApiResponse apiResponse = categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllCategory() {
		ApiResponse apiResponse = categoryService.deleteAllCategory();
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/searchByTitle/{categoryTitle}")
	public ResponseEntity<List<CategoryResponseDto>> searchCategoryWithTitle(
			@PathVariable("categoryTitle") String categoryTitle) {
		List<CategoryResponseDto> categoryResponseDto = categoryService.searchCategoryWithTitle(categoryTitle);
		return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDto);
	}

}
