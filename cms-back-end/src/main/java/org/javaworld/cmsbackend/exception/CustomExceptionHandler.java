package org.javaworld.cmsbackend.exception;

import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	

	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	/**
	 * this method handles exceptions thrown inside controllers
	 */
	@ExceptionHandler({ BadCredentialsException.class, Exception.class })
	public ResponseEntity<Object> handleExceptionController(Exception ex, WebRequest request) {
		
		ExceptionUtil.logExceptionDetails(ex);
		
		Response customResponse = new Response();
		customResponse.setStatus(false);
		customResponse.setMessage(ex.getMessage());
		
		HttpStatus httpStatus = (ex instanceof BadCredentialsException) ? 
				HttpStatus.UNAUTHORIZED : HttpStatus.INTERNAL_SERVER_ERROR;		

		return new ResponseEntity<>(customResponse, httpStatus);
	}

	/**
	 * this method customizes the body of the response when exceptions are thrown by
	 * spring such as HttpMediaTypeNotSupportedException and
	 * MethodArgumentNotValidException
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logger.info(">> rest-api: inside method: handleExceptionInternal(..)");
		
		ExceptionUtil.logExceptionDetails(ex);

		Response customResponse = new Response();
		customResponse.setStatus(false);
		customResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(customResponse, headers, status);
	}
	

}
