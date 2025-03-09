package com.website.blogapp.exception;

public class RoleAlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RoleAlreadyExistException(String msg) {
		super(msg);
	}
}