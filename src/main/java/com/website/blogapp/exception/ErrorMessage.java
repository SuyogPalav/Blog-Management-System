package com.website.blogapp.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorMessage {
	private Date timestamp;
	private Integer statusCode;
	private String message;
	private String description;
	private Boolean success;

}
