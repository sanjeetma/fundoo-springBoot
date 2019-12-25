package com.bridgelabz.fundo.dto;

import javax.persistence.Column;

import lombok.Data;

@Data
public class UserDTO {
    
	@Column(nullable = false)
	private String firstName;
    
	@Column(nullable = false)
	private String lastName;
    
	@Column(nullable = false)
	private String gender;
    
	@Column(nullable = false)
	private String email;
   
	@Column(nullable = false)
	private String password;
    
	@Column(nullable = false)
	private Long mobileNumber;
	
}
