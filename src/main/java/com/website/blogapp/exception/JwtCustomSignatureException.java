package com.website.blogapp.exception;

public class JwtCustomSignatureException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public JwtCustomSignatureException(String msg) {
		super(msg);
	}

}
