package com.website.blogapp.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserDatabaseIsEmptyException.class)
	public ResponseEntity<ErrorMessage> userDatabaseIsEmptyExceptionHandler(UserDatabaseIsEmptyException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

//		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundExceptionHandler(UserNotFoundException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {
		Map<String, String> errorMessage = new HashMap<>();

		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

		for (ObjectError error : allErrors) {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errorMessage.put(fieldName, message);
		}

//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			errorMessage.put(fieldName, message);
//		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

	}

	@ExceptionHandler(CategoryDatabaseIsEmptyException.class)
	public ResponseEntity<ErrorMessage> categoryDatabaseIsEmptyExceptionHandler(CategoryDatabaseIsEmptyException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorMessage> categoryNotFoundExceptionHandler(CategoryNotFoundException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorMessage> noResourceFoundExceptionHandler(NoResourceFoundException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorMessage> httpRequestMethodNotSupportedExceptionHandler(
			HttpRequestMethodNotSupportedException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(),
				"Required request body is missing", webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(PostDatabaseIsEmptyException.class)
	public ResponseEntity<ErrorMessage> postDatabasIsEmptyExceptionHandler(PostDatabaseIsEmptyException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ErrorMessage> postNotFoundExceptionHandler(PostNotFoundException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ErrorMessage> propertyReferenceExceptionHandler(PropertyReferenceException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ErrorMessage> fileNotFoundExceptionHandler(FileNotFoundException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorMessage> iOExceptionHandler(IOException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
	public ResponseEntity<ErrorMessage> methodArgumentConversionNotSupportedExceptionHandler(
			MethodArgumentConversionNotSupportedException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<ErrorMessage> multipartExceptionHandler(MultipartException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ErrorMessage> missingServletRequestPartExceptionHandler(MissingServletRequestPartException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ErrorMessage> missingPathVariableExceptionHandler(MissingPathVariableException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorMessage> missingServletRequestParameterExceptionHandler(
			MissingServletRequestParameterException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
				webRequest.getDescription(false), false);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

}
