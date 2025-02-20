package com.website.blogapp.exception;

public class UserDatabaseIsEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserDatabaseIsEmptyException(String msg) {
		super(msg);
	}

}
