package com.TourGuideApplication.exception;

import java.time.LocalDateTime;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionResponse> handleFeignException(FeignException feignException) {
		ExceptionResponse exceptionResponse = buildFeignExceptionResponse(feignException);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(feignException));
	}
	
	private ExceptionResponse buildFeignExceptionResponse(FeignException fEx) {
		int statusCode = fEx.status();
		HttpStatus status = HttpStatus.valueOf(statusCode);
		String feignExceptionStatus = status.toString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), feignExceptionStatus,
				fEx.getCause().toString(), fEx.getMessage());
		return exceptionResponse;
	}
	
	private HttpStatus getHttpStatusFromException(Exception ex) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.value();
		return status;
	}

}
