package com.bridgelabz.fundo.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RabbitMessage implements Serializable {

	private static final long serialVersionUID = -8904803216597895332L;
	private String email;
	private String link;
	private String token;
}
