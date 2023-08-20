package com.thejobslk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> userExceptiionHandler(UserException userException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(userException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorDetails> loginExceptiionHandler(LoginException loginException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(loginException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AppointmentException.class)
	public ResponseEntity<MyErrorDetails> AppointmentExceptiionHandler(AppointmentException appointmentException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(appointmentException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConsultantException.class)
	public ResponseEntity<MyErrorDetails> consultantExceptiionHandler(ConsultantException consultantException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(consultantException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TimeDateException.class)
	public ResponseEntity<MyErrorDetails> timeDateExceptiionHandler(TimeDateException timeDateException, WebRequest webRequest){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(timeDateException.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> constraintExceptionHandler(MethodArgumentNotValidException constraintViolationException, WebRequest webRequest){
		
		List<String> details = new ArrayList<>();
		
		for(ObjectError error : constraintViolationException.getBindingResult().getAllErrors()) {
			
		      details.add(error.getDefaultMessage());
		      
		}
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		
		
		myErrorDetails.setErrorMsg(details.get(0));
		
		
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> handleAllException(Exception exception, WebRequest webRequest) {
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setDetails(webRequest.getDescription(false));
		myErrorDetails.setErrorMsg(exception.getMessage());
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
