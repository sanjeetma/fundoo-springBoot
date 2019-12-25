package com.bridgelabz.fundo.controller;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.LabelDTO;
import com.bridgelabz.fundo.dto.NoteDTO;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.model.NoteModel;
//import com.bridgelabz.fundo.service.ElasticSearchService;
import com.bridgelabz.fundo.service.NoteServiceImplementation;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/note")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "jwtToken" })
public class NoteController {
	@Autowired
	private NoteServiceImplementation noteServiceImplementation;
	
//	@Autowired
//	ElasticSearchService elasticService;

	@PostMapping("/createnote")
	public NoteModel createNote(@RequestHeader String token, @RequestBody NoteDTO noteDto) {
		return noteServiceImplementation.createNote(token, noteDto);
	}

	@PutMapping("/updatenote")
	public ResponseEntity<String> updateNode(@RequestHeader String token,  @RequestBody NoteModel noteModel) {
		String result = noteServiceImplementation.updateNode(token, noteModel);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@PutMapping("/updatenotereact")
	public ResponseEntity<String> updateNodeReact(@RequestParam Long id,@RequestBody NoteModel noteModel,@RequestHeader String token) {
		String result = noteServiceImplementation.updateNode(id, noteModel,token);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/deletenote")
	public ResponseEntity<String>  deleteNode( @RequestParam Long id, @RequestHeader String token) {
		String result= noteServiceImplementation.deleteNode(token, id);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/retrieveallnote")
	public List<NoteModel> retrieveAllNode(@RequestHeader String token) {
		List<NoteModel> updatelist= noteServiceImplementation.retrieveAllNode(token);
		return updatelist;
	}
	
	@PutMapping("/updatearchive")
	public String updateArchive(@RequestParam Long noteId, @RequestHeader String token) {
		return noteServiceImplementation.updateArchive(noteId,token);
	}

	@PutMapping("/updatepinned")
	public String updatePinned(@RequestParam Long id,@RequestHeader String token) {
		System.err.println("Hello Pinned");
		return noteServiceImplementation.updatePinned(id,token);
	}

	@PutMapping("/updatetrash")
	public ResponseEntity<String> updateinTrash(@RequestParam Long id,@RequestHeader String token) {
		String result= noteServiceImplementation.updateinTrash(token, id);
		System.out.println(result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	@PostMapping("/mappingNoteLabel")
	public ResponseEntity<Response> listLabel( @RequestHeader(name = "token") String token ,@RequestParam Long noteId,@RequestBody LabelDTO labeldto) {
		System.out.println("label is11111111111.........."+labeldto.getLabel());
		String status= noteServiceImplementation.listLabel(noteId, labeldto, token);
		Response response=new Response(HttpStatus.ACCEPTED.value(),status);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@DeleteMapping("/deleteLabelFromNote")
	public ResponseEntity<Response> deleteLabelFromNote(@RequestParam Long noteId,@RequestParam Long labelId,@RequestHeader String token)
	{
		String status= noteServiceImplementation.deleteLabelFromNote(token, noteId, labelId);
		Response response=new Response(HttpStatus.ACCEPTED.value(),status);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
//	
	@DeleteMapping("/delete_note")
	public ResponseEntity<NoteModel> deleteNote(@RequestParam Long noteId, @RequestHeader String token) {
	NoteModel notes = noteServiceImplementation.deleteNode(noteId, token);
	return new ResponseEntity<NoteModel>(notes, HttpStatus.OK);
	}
	@PutMapping("/change-color/{color}")
	public ResponseEntity<NoteModel> updateColor(@PathVariable("color") String color,
	@RequestParam("noteId") Long noteId, @RequestHeader String token) {
	System.out.println("reched inside update color method");
	System.out.println(noteId);
	
	System.out.println(color);
	System.out.println(token);
 NoteModel notes = noteServiceImplementation.updateColor(noteId, color, token);
	return new ResponseEntity<NoteModel>(notes, HttpStatus.OK);
	}
	@PutMapping("/reminder")
	public ResponseEntity<NoteModel> updateReminder(@RequestParam Long noteId,
	@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminderDate,
	@RequestHeader String token) {
	System.out.println(reminderDate);
	NoteModel notes = noteServiceImplementation.updateReminder(reminderDate, token, noteId);
	return new ResponseEntity<NoteModel>(notes, HttpStatus.OK);
	}
	@GetMapping("/get_reminder")
	public ResponseEntity<List<NoteModel>> getReminders(@RequestHeader String token)
	{
	System.out.println("inside getReminders method");
	List<NoteModel> notes= noteServiceImplementation.getReminder(token);
	return new ResponseEntity<List<NoteModel>>(notes, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteRemainder")
	public ResponseEntity<Response> deleteRemainder(@RequestParam Long noteId, @RequestHeader String token){
		String result=noteServiceImplementation.deleteRemainder(noteId, token);
		Response response=new Response(HttpStatus.OK.value(),result);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping("/get_archivednotes")
	public ResponseEntity<List<NoteModel>> getArchivedNotes(@RequestHeader String token)
	{
	System.out.println("inside getarchived notes method");
	List<NoteModel> notes= noteServiceImplementation.getArchivedNotes(token);
	return new ResponseEntity<List<NoteModel>>(notes, HttpStatus.OK);
	}
	
	
	@GetMapping("/get_trash")
	public ResponseEntity<List<NoteModel>> getTrash(@RequestHeader String token)
	{
	System.out.println("inside getTrash method");
	List<NoteModel> notes= noteServiceImplementation.getTrash(token);
	return new ResponseEntity<List<NoteModel>>(notes, HttpStatus.OK);
	}
	
	@GetMapping("/getNotesOnLabel")
	public List<NoteModel> getNotesOnLabel(@RequestParam Long labelId, @RequestHeader String token){
		System.out.println(labelId+"     ");
		List<NoteModel> notes=noteServiceImplementation.getNotesOnLabel(labelId, token);
		return notes;
	}
	
	@PostMapping("/addcollaborator")
	public ResponseEntity<LabelModel> addCollaborator(@RequestParam Long noteId,@RequestParam String email, @RequestHeader String token) {
	System.out.println("inside colaborator");
	noteServiceImplementation.addCollaborater(noteId, email, token);
	System.out.println("collaborator added");
	return new ResponseEntity<LabelModel>(HttpStatus.OK);
	}
	
	@DeleteMapping("/removecollaborator")
	public ResponseEntity<Response> removeCollaborator(@RequestParam Long noteId, @RequestParam String email, @RequestHeader String token) {
	String msg=noteServiceImplementation.removeCollaborater(token, email, noteId);
	System.out.println("collaborator removed");
	Response response=new Response(HttpStatus.OK.value(),msg);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/showallcolabrators")
	public ResponseEntity<List<String>> colabratorList(@RequestParam Long noteId, @RequestHeader String token) {
	List<String> collaboratedUsers = noteServiceImplementation.getCollaboratedUserForNotes(noteId, token);
	return new ResponseEntity<List<String>>(collaboratedUsers, HttpStatus.OK);
	}
	
	@GetMapping("/getCollaboratedUser")
	public ResponseEntity<List<String>> getCollboratedUserForNote(@RequestParam Long noteId, @RequestHeader String token)
	{
	List<String> collaboratedUsers = noteServiceImplementation.getCollaboratedUserForNotes(noteId, token);
	return new ResponseEntity<List<String>>(collaboratedUsers, HttpStatus.OK);
	}
	
//	@GetMapping("/search_note")
//	public List<NoteModel> searchNoteByData(@RequestParam String data)
//	{
//		return elasticService.searchNoteByData(data);
//	}
	
	@PutMapping("/addNotesLabel")
	public ResponseEntity<Response> addNoteToLabel(@RequestParam Long labelId, 
			@RequestParam Long noteId, @RequestHeader String token) {
		String result=noteServiceImplementation.addNoteToLabel(labelId,noteId, token);
		Response response=new Response(HttpStatus.ACCEPTED.value(),result);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
@GetMapping("/fetcharchivenote")
public List<NoteModel> fetchNotesFromArchive(@RequestHeader String token)
{
	return noteServiceImplementation.fetchNotesFromArchive(token);
}
}
