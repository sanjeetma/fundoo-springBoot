package com.bridgelabz.fundoo.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Component
@AllArgsConstructor
public class Response {
	private int code;
	private String statusMessage;
	
}
