package com.bridgelabz.fundo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponce>generalException(Exception e) throws Exception{
		ExceptionResponce exceptionResponce=new ExceptionResponce();
		exceptionResponce.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exceptionResponce.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponce>(exceptionResponce,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponce>registrationException(CustomException e) {
		ExceptionResponce exceptionResponce=new ExceptionResponce();
		exceptionResponce.setCode(HttpStatus.BAD_REQUEST.value());
		exceptionResponce.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponce>(exceptionResponce,HttpStatus.BAD_REQUEST); 
	}
	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<ExceptionResponce>loginException(InvalidCredentialException e) {
		ExceptionResponce exceptionResponce=new ExceptionResponce();
		exceptionResponce.setCode(HttpStatus.NOT_FOUND.value());
		exceptionResponce.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponce>(exceptionResponce,HttpStatus.NOT_FOUND); 
	}
	@ExceptionHandler(ForgotException.class)
	public ResponseEntity<ExceptionResponce>forgetException(ForgotException e) {
		ExceptionResponce exceptionResponce=new ExceptionResponce();
		exceptionResponce.setCode(HttpStatus.NOT_ACCEPTABLE.value());
		exceptionResponce.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponce>(exceptionResponce,HttpStatus.NOT_ACCEPTABLE); 
	}
	@ExceptionHandler(ResetException.class)
	public ResponseEntity<ExceptionResponce>resetException(ResetException e) {
		ExceptionResponce exceptionResponce=new ExceptionResponce();
		exceptionResponce.setCode(HttpStatus.CONFLICT.value());
		exceptionResponce.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponce>(exceptionResponce,HttpStatus.CONFLICT); 
	}
	
}
