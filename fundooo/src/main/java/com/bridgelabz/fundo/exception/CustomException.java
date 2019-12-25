package com.bridgelabz.fundo.exception;

public class CustomException extends RuntimeException {
private static final long serialVersionUID=1l;
private int code;
private String message;
private Object data;
public CustomException(int code, String message) {
	this.code=code;
	this.message=message;
}

public CustomException(int code, String message, Object data) {
	super();
	this.code = code;
	this.message = message;
	this.data = data;
}

public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}

}
