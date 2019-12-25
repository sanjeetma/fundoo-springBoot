package com.bridgelabz.fundo.exception;

public class ResetException extends RuntimeException{
	private static final long serialVersionUID=1l;
	private int code;
	private String message;
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
	public ResetException(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
