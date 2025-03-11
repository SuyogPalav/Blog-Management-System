package com.website.blogapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {
	
	private Integer categoryId;
	private String categoryTitle;
	private String categoryDesc;
	
}
