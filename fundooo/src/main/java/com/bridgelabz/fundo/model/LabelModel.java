package com.bridgelabz.fundo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class LabelModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long labelId;
	
	@Column(nullable = false, unique = true)
	private String label;
	 
}
