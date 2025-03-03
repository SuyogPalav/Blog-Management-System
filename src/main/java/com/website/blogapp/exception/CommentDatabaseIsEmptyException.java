package com.website.blogapp.exception;

public class CommentDatabaseIsEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CommentDatabaseIsEmptyException(String msg) {
		super(msg);
	}
}