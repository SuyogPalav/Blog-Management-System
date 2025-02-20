package com.website.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.website.blogapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	@Query(value = "SELECT * FROM categories WHERE category_title LIKE CONCAT('%', :categoryTitle, '%')", nativeQuery = true) // MySQL
	public List<Category> searchByCategoryTitle(@Param("categoryTitle") String categoryTitle);

}
