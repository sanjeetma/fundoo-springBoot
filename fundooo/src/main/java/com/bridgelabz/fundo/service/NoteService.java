package com.bridgelabz.fundo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bridgelabz.fundo.dto.LabelDTO;
import com.bridgelabz.fundo.dto.NoteDTO;
import com.bridgelabz.fundo.model.NoteModel;

public interface NoteService {
	public NoteModel createNote(String token, NoteDTO noteDto);
	public String updateNode(String token, NoteModel noteModel);
	public String updateNode(Long id,NoteModel noteModel,String token);
	public String deleteNode(String token,Long id);
	public String updateArchive(Long id, String token);
	public List<NoteModel> fetchNotesFromArchive(String token);
	public String updatePinned(Long id,String token);
	public String updateinTrash(String token, Long id);
	public List<NoteModel> retrieveAllNode(String token);
	public String listLabel( Long noteId, LabelDTO labeldto, String token);
	public String addNoteToLabel(Long noteId, Long labelId, String token  );
	//public String deleteLabelFromNote(String token, Long noteId, LabelDTO labeldto);
	public String deleteLabelFromNote(String token, Long noteId, Long labelId);
	public String addCollaborater( Long noteId, String email,String token);
	public String removeCollaborater(String token, String email, Long noteId);
	public NoteModel updateColor(Long noteId, String color, String token);
	public  NoteModel updateReminder(LocalDateTime reminderDate, String token, Long noteId);
	public List<NoteModel> getReminder(String token);
	public List<NoteModel> getArchivedNotes(String token);
	public List<NoteModel> getTrash(String token);
	public NoteModel deleteNode(Long noteId, String token);
	public String deleteRemainder(Long noteId, String token);
	public List<NoteModel> getNotesOnLabel(Long labelId, String token);
	List<String> getCollaboratedUserForNotes(Long noteId, String token);
	
	
}
