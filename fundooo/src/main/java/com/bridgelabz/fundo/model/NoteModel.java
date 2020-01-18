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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class NoteModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noteId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	//@Column(columnDefinition = "TINYINT(1) default 0")
	private String colour;
	
	@Column(nullable = false)
	private LocalDateTime creadtedTime;
	
	@Column(nullable = false)
	private LocalDateTime updateTime;
	
	private LocalDateTime remindMe;
	
	@Column(nullable = false)
	private boolean isPinned;
	
	@Column(nullable = false)
	private boolean inTrash;
	
	@Column(nullable = false)
	private boolean isArchive;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "noteId")
	List<LabelModel> labelModel;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserModel> collabratorUserList;
	
}
