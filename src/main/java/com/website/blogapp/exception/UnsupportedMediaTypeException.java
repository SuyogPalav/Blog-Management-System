package com.website.blogapp.exception;

public class UnsupportedMediaTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnsupportedMediaTypeException(String msg) {
		super(msg);
	}

}
