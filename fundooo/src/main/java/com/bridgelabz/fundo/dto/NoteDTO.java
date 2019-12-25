package com.bridgelabz.fundo.dto;

import javax.persistence.Column;

import lombok.Data;

@Data
public class NoteDTO {
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String colour;
	
}
