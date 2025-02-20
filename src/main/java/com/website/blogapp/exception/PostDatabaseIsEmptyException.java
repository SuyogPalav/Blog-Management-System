package com.website.blogapp.exception;

public class PostDatabaseIsEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PostDatabaseIsEmptyException(String msg) {
		super(msg);
	}
}
