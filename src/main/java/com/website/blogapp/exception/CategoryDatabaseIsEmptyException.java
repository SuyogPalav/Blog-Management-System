package com.website.blogapp.exception;

public class CategoryDatabaseIsEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CategoryDatabaseIsEmptyException(String msg) {
		super(msg);
	}
	
}
