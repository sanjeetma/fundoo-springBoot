package com.bridgelabz.fundo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
	private String msg;
	private int statusCode;

}
