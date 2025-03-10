package com.website.blogapp.exception;

public class AdminRoleNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AdminRoleNotFoundException(String msg) {
		super(msg);
	}

}
