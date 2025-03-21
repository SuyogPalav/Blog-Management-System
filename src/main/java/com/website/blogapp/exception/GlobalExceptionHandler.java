package com.website.blogapp.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
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

import com.fasterxml.jackson.core.JsonParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserDatabaseIsEmptyException.class)
	public ResponseEntity<ErrorMessage> userDatabaseIsEmptyExceptionHandler(UserDatabaseIsEmptyException ex,
			WebRequest webRequest) {
//		ErrorMessage errorMessage = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
//				webRequest.getDescription(false), false);

		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

//		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundExceptionHandler(UserNotFoundException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

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
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorMessage> categoryNotFoundExceptionHandler(CategoryNotFoundException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorMessage> noResourceFoundExceptionHandler(NoResourceFoundException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorMessage> httpRequestMethodNotSupportedExceptionHandler(
			HttpRequestMethodNotSupportedException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessage> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value()).message("Required request body is missing")
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

	}

	@ExceptionHandler(PostDatabaseIsEmptyException.class)
	public ResponseEntity<ErrorMessage> postDatabasIsEmptyExceptionHandler(PostDatabaseIsEmptyException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ErrorMessage> postNotFoundExceptionHandler(PostNotFoundException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ErrorMessage> propertyReferenceExceptionHandler(PropertyReferenceException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ErrorMessage> fileNotFoundExceptionHandler(FileNotFoundException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorMessage> iOExceptionHandler(IOException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
	public ResponseEntity<ErrorMessage> methodArgumentConversionNotSupportedExceptionHandler(
			MethodArgumentConversionNotSupportedException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<ErrorMessage> multipartExceptionHandler(MultipartException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ErrorMessage> missingServletRequestPartExceptionHandler(MissingServletRequestPartException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ErrorMessage> missingPathVariableExceptionHandler(MissingPathVariableException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorMessage> missingServletRequestParameterExceptionHandler(
			MissingServletRequestParameterException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.NOT_FOUND.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

	}

	@ExceptionHandler(FileAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> fileAlreadyExistsExceptionHandler(FileAlreadyExistsException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date()).statusCode(HttpStatus.CONFLICT.value())
				.message(ex.getMessage()).description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorMessage> badCredentialsExceptionHandler(BadCredentialsException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);

	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> accessDeniedExceptionHandler(AccessDeniedException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.FORBIDDEN.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);

	}

	@ExceptionHandler(AccountStatusException.class)
	public ResponseEntity<ErrorMessage> accountStatusExceptionHandler(AccountStatusException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.FORBIDDEN.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);

	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorMessage> authorizationDeniedExceptionHandler(AuthorizationDeniedException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);

	}

	@ExceptionHandler(RoleAlreadyExistException.class)
	public ResponseEntity<ErrorMessage> roleAlreadyExistExceptionHandler(RoleAlreadyExistException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);

	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorMessage> noSuchElementExceptionHandler(NoSuchElementException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);

	}

	@ExceptionHandler(InterruptedException.class)
	public ResponseEntity<ErrorMessage> interruptedExceptionHandler(InterruptedException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage())
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

	}

	@ExceptionHandler(DuplicateUserEmailFoundException.class)
	public ResponseEntity<ErrorMessage> duplicateUserEmailFoundExceptionHandler(DuplicateUserEmailFoundException ex,
			WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date()).statusCode(HttpStatus.CONFLICT.value())
				.message(ex.getMessage()).description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);

	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ErrorMessage> jsonParseExceptionHandler(JsonParseException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message("Invalid JSON format in 'postDto'. Please provide a correctly formatted JSON string")
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<ErrorMessage> httpMediaTypeNotAcceptableExceptionHandler(
			HttpMediaTypeNotAcceptableException ex, WebRequest webRequest) {
		ErrorMessage errorMessage = ErrorMessage.builder().timestamp(new Date())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message("Invalid JSON format in 'postDto'. Please provide a correctly formatted JSON string")
				.description(webRequest.getDescription(false)).success(false).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

	}

}
