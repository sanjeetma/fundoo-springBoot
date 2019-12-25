package com.bridgelabz.fundo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private Long mobileNumber;

	@Column(nullable = false)
	private LocalDateTime currentTime;

	@Column(nullable = false)
	private LocalDateTime updateTime;

	@Column(nullable = false)
	private boolean isVerify;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private List<NoteModel> note = new ArrayList<NoteModel>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
//	@JsonBackReference
	private List<LabelModel> label = new ArrayList<LabelModel>();
	
	
	
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "collabratorUserList")
	@JsonIgnore
//	@JsonBackReference
	private List<NoteModel> collabratorNoteList;
	
}












