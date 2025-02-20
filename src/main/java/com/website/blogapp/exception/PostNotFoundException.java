package com.website.blogapp.exception;

public class PostNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String msg) {
		super(msg);
	}
}
