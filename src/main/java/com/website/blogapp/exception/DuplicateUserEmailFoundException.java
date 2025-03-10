package com.website.blogapp.exception;

public class DuplicateUserEmailFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DuplicateUserEmailFoundException(String msg) {
		super(msg);
	}

}
