package com.bridgelabz.fundo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.LabelDTO;
import com.bridgelabz.fundo.dto.NoteDTO;
import com.bridgelabz.fundo.exception.CustomException;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.model.NoteModel;
import com.bridgelabz.fundo.model.UserModel;
import com.bridgelabz.fundo.repository.LabelRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.JWTUtility;

@Service
public class NoteServiceImplementation implements NoteService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepositorty;

	@Autowired
	private JWTUtility jwtUtlity;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LabelService labelService;
	
	@Autowired
	ElasticSearchService elasticSearchService;

//	 @Autowired(required=true)
//	 private com.bridgelabz.fundo.model.NoteModel noteModel;

	@Autowired
	private com.bridgelabz.fundo.repository.NoteRepository noteRepository;

	@Override
	public NoteModel createNote(String token, NoteDTO noteDto) {
		Long varifiedUserId = jwtUtlity.verify(token);
		Optional<UserModel> userModel = userRepositorty.findById(varifiedUserId);
		NoteModel noteModel = modelMapper.map(noteDto, NoteModel.class);
		if (userModel.isPresent()) {
			noteModel.setCreadtedTime(LocalDateTime.now());
			noteModel.setUpdateTime(LocalDateTime.now());
			userModel.get().getNote().add(noteModel);
			noteRepository.save(noteModel);
			elasticSearchService.createNote(noteModel);
		}
		return noteModel;
	}

//	@Override
//	public String updateNode(NoteModel noteModel,String token) {
//		String message = null;
//		Long varifiedUserId = jwtUtlity.verify(token);
//		Optional<UserModel> userModel = userRepositorty.findById(varifiedUserId);
//		if (userModel.isPresent() && userModel.get().isVerify()) {
//			Optional<NoteModel> noteModel = noteRepository.findById(id);
//			if (noteModel.isPresent()) {
//				noteModel.get().setTitle(noteDto.getTitle());
//				noteModel.get().setDescription(noteDto.getDescription());
//				noteModel.get().setColour(noteDto.getColour());
//				noteModel.get().setUpdateTime(LocalDateTime.now());
//				noteRepository.save(noteModel.get());
//				message = "note updated";
//			} else {
//				message = "note is not present";
//			}
//		} else {
//			message = "user is not exit";
//		}
//		return message;
//	}
	
	@Override
	public String updateNode(String token,NoteModel noteModel) {
		Long userId = jwtUtlity.verify(token);
		Optional<UserModel> userDetails = userRepositorty.findById(userId);
		Optional<NoteModel> note = null;
		if (userDetails.isPresent()) {
		note = noteRepository.findById(noteModel.getNoteId());
		if (note.isPresent() && userDetails.get().isVerify()) {
		note.get().setTitle(noteModel.getTitle());
		note.get().setDescription(noteModel.getDescription());
		note.get().setUpdateTime(LocalDateTime.now());
		noteRepository.save(note.get());
		elasticSearchService.updateNote(note.get());
		} else
		throw new CustomException(104, "Invalid token or user not verified");
		} else
		throw new CustomException(102, "User not exist");
		
		return token;
		
	}
	
	@Override
	public String updateNode(Long id, NoteModel noteModel, String token) {
		Long userId = jwtUtlity.verify(token);
		Optional<UserModel> userDetails = userRepositorty.findById(userId);
		Optional<NoteModel> note = null;
		if (userDetails.isPresent()) {
		note = noteRepository.findById(id);
		if (note.isPresent() && userDetails.get().isVerify()) {
		note.get().setTitle(noteModel.getTitle());
		note.get().setDescription(noteModel.getDescription());
		note.get().setUpdateTime(LocalDateTime.now());
		noteRepository.save(note.get());
		System.err.println(note.get());
		System.err.println("update note done");
		} else
		throw new CustomException(104, "Invalid token or user not verified");
		} else
		throw new CustomException(102, "User not exist");
		System.err.println("update note");
		return "update note";
		
	}
	
	

	@Override
	public String deleteNode(String token, Long id) {// same
		Long varifiedUserId = jwtUtlity.verify(token);
		Optional<UserModel> userModel = userRepositorty.findById(varifiedUserId);
		Optional<NoteModel> noteModel = noteRepository.findById(id);
		String message = null;
		if (userModel.isPresent()) {
			
			if (noteModel.isPresent()) {
				noteRepository.deleteById(id);
				elasticSearchService.deleteNote(noteModel.get());
				message = "note deleted";
			} else {
				message = "note is not present";
			}
		} else {
			message = "user is not present";
		}
		return message;
	}

	@Override
	public String updateArchive( Long id,String token) {
		Long verifiedUserId = jwtUtlity.verify(token);
		Optional<UserModel> userModel = userRepositorty.findById(verifiedUserId);
		String message = null;
		if (userModel.isPresent() && userModel.get().isVerify()) {
			Optional<NoteModel> noteModel = noteRepository.findById(id);
			if (noteModel.isPresent() && userModel.isPresent()) {
				if (noteModel.get().isArchive()) {
					noteModel.get().setArchive(false);
					noteRepository.save(noteModel.get());
					message = "archive update is done";
				} else {
					noteModel.get().setArchive(true);
					noteRepository.save(noteModel.get());
					message = "archive update is done";
				}
			} else {
				message = "Note is not present";
			}
		} else {
			message = "User not Present";
		}
		return message;
	}

	@Override
	public String updatePinned(Long id, String token) {
		Long verifiedUserId = jwtUtlity.verify(token);
		Optional<UserModel> userModel = userRepositorty.findById(verifiedUserId);
		System.err.println(userModel);
		String message = null;
		if (userModel.isPresent()) {
			Optional<NoteModel> noteModel = noteRepository.findById(id);
			System.err.println(noteModel);
			if (noteModel.isPresent()) {
				if (noteModel.get().isPinned()) {
					noteModel.get().setPinned(false);
					noteRepository.save(noteModel.get());
					message = "pin update is done";
				} else {
					noteModel.get().setPinned(true);
					noteRepository.save(noteModel.get());
					message = "pin update is done";
				}
			} else {
				message = "pin is not present";
			}
		} else {
			message = "User not Present";
		}
		return message;
	}

	@Override
	public String updateinTrash(String token, Long id) {
		Long verifiedUserId = jwtUtlity.verify(token);
		Optional<UserModel> userModel = userRepositorty.findById(verifiedUserId);
		String message = null;
		if (userModel.isPresent() && userModel.get().isVerify()) {
			Optional<NoteModel> noteModel = noteRepository.findById(id);
			if (noteModel.isPresent()) {
				if (noteModel.get().isInTrash()) {
					noteModel.get().setInTrash(false);
					noteRepository.save(noteModel.get());
					message = "trash update is done";
				} else {
					noteModel.get().setInTrash(true);
					noteRepository.save(noteModel.get());
					message = "trash update is done";
				}
			} else {
				message = "Note is not present";
			}
		} else {
			message = "User not Present";
		}
		return message;
	}

	@Override
	public List<NoteModel> retrieveAllNode(String token) {
		Long verifiedUserId = jwtUtlity.verify(token);
	
		List<NoteModel> note = null;
		Optional<UserModel> userModel = userRepositorty.findById(verifiedUserId);
		if (userModel.isPresent()) {
			note = userModel.get().getNote();
			note=note.stream().filter(data -> !data.isArchive() && !data.isInTrash()).collect(Collectors.toList());
			List<NoteModel> collaboratendnotes=getCollaboratedNoteForUser(verifiedUserId);
			if(!collaboratendnotes.isEmpty()) {
				for(NoteModel notes : collaboratendnotes) {
					note.add(notes);
				}
			}
		}
		return note;
	}

	@Override
	public String listLabel(Long noteId, LabelDTO labeldto, String token) {
		Long verifiedUser = jwtUtlity.verify(token);
		System.out.println("in service");
		Optional<UserModel> userModel = userRepositorty.findById(verifiedUser);
		if (userModel.isPresent()) {
			LabelModel label=new LabelModel();
			BeanUtils.copyProperties(labeldto, label);
			//Label labelModel=modelMapper.map(labeldto,Label.class);
			Long verifieduser=jwtUtlity.verify(token);
			Optional<UserModel> userlist=userRepositorty.findById(verifieduser);
			Optional<NoteModel> notelist=noteRepository.findById(noteId);
			
			userlist.get().getLabel().add(label);
			notelist.get().getLabelModel().add(label);
			labelRepository.save(label);
			
			
			return "done";
		}

		return "user not present";
	}

	@Override
	public String addNoteToLabel( Long labelId, Long noteId,String token) {
		Long userId = jwtUtlity.verify(token);
		Optional<UserModel> userDetails = userRepositorty.findById(userId);
		Optional<NoteModel> noteDetails=null;
		Optional<LabelModel> labelDetails=null;
		if(userDetails.isPresent()) {
			noteDetails=noteRepository.findById(noteId);
			if(noteDetails.isPresent())
			{
				labelDetails=labelRepository.findById(labelId);
				if(labelDetails.isPresent()) {
					noteDetails.get().getLabelModel().add(labelDetails.get());
				}
				else {
					return "label not present";
				}
			}
			else {
				return "note not exit";
			}
		}
		else {
			return "user not exit";
		}
		return null;
	}

	
	//@Override
//	public String addNoteToLabel(Long noteId, Long labelId, String token) {
//		Long verifiedUser = jwtUtlity.verify(token);
//		System.out.println("in service");
//		Optional<UserModel> userModel = userRepositorty.findById(verifiedUser);
//		if (userModel.isPresent()) {
//			System.out.println("in user present");
//			Optional<LabelModel> isLabelPresent = userModel.get().getLabel().stream()
//					.filter(data -> data.getLabel().equalsIgnoreCase(labeldto.getLabel())).findFirst();
//			if (isLabelPresent.isPresent()) {
//				System.out.println("in label present");
//				Optional<NoteModel> noteModel = noteRepository.findById(noteId);
//				if (noteModel.isPresent()) {
//					System.out.println("in note present");
//					
//					Optional<LabelModel> isnotelabelPresent=noteModel.get().getLabelModel()
//							.stream().filter(data->data.getLabel().equalsIgnoreCase(labeldto.getLabel())).findFirst();
//					
//					if(isnotelabelPresent.isPresent())
//					{
//						throw new CustomException(HttpStatus.BAD_REQUEST.value(), "label already exist");
//					}
//					noteModel.get().getLabelModel().add(isLabelPresent.get());
//				
//					noteRepository.save(noteModel.get());
//					return "label added to note";
//				}
//			}
//			else {
//					labelService.createLabel(token, labeldto);
//					String response=listLabel(token, noteId, labeldto);
//					return response;
//				}
//			
//		}
////		return "user not present";
//		return null;
//	}
	
	@Override
	public String deleteLabelFromNote(String token, Long noteId, Long labelId) {
	Long userVerified=jwtUtlity.verify(token);
	Optional<UserModel> userModel=userRepositorty.findById(userVerified);
	if(userModel.isPresent()) {
		Optional<NoteModel> noteModel = noteRepository.findById(noteId);
		if(noteModel.isPresent()) {
			Optional<LabelModel> isnotelabelPresent=noteModel.get().getLabelModel().stream()
					.filter(data->data.getLabelId()==labelId).findFirst();
			if(isnotelabelPresent.isPresent())
			{
				noteModel.get().getLabelModel().remove(isnotelabelPresent.get());
				noteRepository.save(noteModel.get());
				return "label deleted";
			}
		}
	}
		return "something wrong";
	}

	@Override
	@Transactional
	public String addCollaborater( Long noteId, String email,String token) {
	Long userId =jwtUtlity.verify(token);

	if (userId.equals(null)) {
	throw new CustomException(400, "User not found 1");
	}
	Optional<UserModel> user = userRepositorty.findById(userId);
	Optional<UserModel> checkUser = userRepositorty.findByEmail(email);
	
	if (!checkUser.isPresent()) {
	throw new CustomException(400, "User not found 2");
	}
	
	 Optional<NoteModel>  checkNote = user.get().getNote().stream().filter(data -> data.getNoteId() == (long)noteId).findFirst();
	if (!checkNote.isPresent()) {
	throw new CustomException(400, "User not found 4");
	}
	Optional<NoteModel> collaboratedNote = checkUser.get().getCollabratorNoteList().stream()
	.filter(data -> data.getNoteId() == noteId).findFirst();
	if (collaboratedNote.isPresent()) {
	throw new CustomException(400, "User not found 5");
	}
	user.get().getCollabratorNoteList().add(checkNote.get());
	checkUser.get().getCollabratorNoteList().add(checkNote.get());
	checkNote.get().getCollabratorUserList().add(checkUser.get());
	noteRepository.save(checkNote.get());
	userRepositorty.save(user.get());
	return "User Collaborated";
	}

	
	@Override
	public List<String> getCollaboratedUserForNotes(Long noteId, String token)
	{
	Long userId = jwtUtlity.verify(token);
	System.out.println("in service");
	ArrayList<String> collabUserList = new ArrayList<String>();
	Optional<UserModel> userModel = userRepositorty.findById(userId);
	if (!userModel.isPresent()) {
	throw new CustomException(400, "User not found 1");
	}
	List<Long> collaUserIds = noteRepository.findBycollabratorNoteId(noteId);
	for(Long eachUserId: collaUserIds)
	{
	Optional<UserModel> user = userRepositorty.findById(eachUserId);
	collabUserList.add(user.get().getEmail());
	}
	return collabUserList;
	}
	
	@Override
	public String removeCollaborater(String token, String email, Long noteId) {
	Long userId = jwtUtlity.verify(token);

	if (userId.equals(null)) {
	throw new CustomException(400, "User not found 1");
	}
	Optional<UserModel> user = userRepositorty.findById(userId);
	Optional<UserModel> checkUser = userRepositorty.findByEmail(email);

	if (!checkUser.isPresent()) {
	throw new CustomException(400, "collaborated user not found 2");
	}
	// Optional<UserNotes> checkNote = user.get().getNotes().stream().filter(data -> data.getId() == noteId)
	// .findFirst();
	// if (!checkNote.isPresent()) {
	// throw new UserExistanceException(400, "User not found 3");
	// }
	Optional<NoteModel> notePresent = noteRepository.findById(noteId);

	Optional<NoteModel> collaboratedNote = checkUser.get().getCollabratorNoteList().stream()
	.filter(data -> data.getNoteId() == noteId).findFirst();
	if (!collaboratedNote.isPresent()) {
	throw new CustomException(400, "collaborated note  not found 4");
	}
	notePresent.get().getCollabratorUserList().remove(checkUser.get());
	checkUser.get().getCollabratorNoteList().remove(collaboratedNote.get());
	noteRepository.save(notePresent.get());
	userRepositorty.save(user.get());
	return "collaborated user removed";
	}

	
	private List<NoteModel> getCollaboratedNoteForUser(Long userId) {
		List<NoteModel> collaborateNotes = new ArrayList<NoteModel>();
		List<Long> collaboratednoteId = noteRepository.findBycollabratorUserList(userId);
		if (collaboratednoteId == null) {
		throw new CustomException(HttpStatus.NOT_ACCEPTABLE.value(), "list is empty");
		}
		for (long noteid : collaboratednoteId) {
		Optional<NoteModel> collaboratedNote = noteRepository.findById(noteid);
		collaborateNotes.add(collaboratedNote.get());
		}
		// List<UserNotes> collaborateNotes1=noteRepository.findAllNote(collaboratednoteId);
		// return collaborateNotes1;
		return collaborateNotes;
		}
	
	@Override
	public NoteModel updateColor(Long noteId, String color, String token) {
	System.out.println("color: " + color);
	Long userId = jwtUtlity.verify(token);
	Optional<UserModel> userDetails = userRepositorty.findById(userId);
	NoteModel userNote = null;
	if (userDetails.isPresent()) {
	Optional<NoteModel> notes = noteRepository.findById(noteId);
	if (notes.isPresent() && userDetails.get().isVerify()) {
	userNote = notes.get();
	userNote.setColour(color);
	userNote.setUpdateTime(LocalDateTime.now());
	noteRepository.save(userNote);
	} else
	throw new CustomException(104, "Invalid token or user not verified");
	} else
	throw new CustomException(102, "User not exist");
	return userNote;
	}

	@Override
	public NoteModel updateReminder(LocalDateTime reminderDate, String token, Long noteId) {
		Long userId = jwtUtlity.verify(token);
		Optional<UserModel> userDetails = userRepositorty.findById(userId);
	    NoteModel userNote = null;
		if (userDetails.isPresent()) {
		Optional<NoteModel> notes = noteRepository.findById(noteId);
		if (notes.isPresent() && userDetails.get().isVerify()) {
		userNote = notes.get();
		userNote.setRemindMe(reminderDate);
		userNote.setUpdateTime(LocalDateTime.now());
		noteRepository.save(userNote);
		} else
		throw new CustomException(104, "Invalid token or user not verified");
		} else
		throw new CustomException(102, "User not exist");
		return userNote;
		}

	@Override
	public List<NoteModel> getReminder(String token) {
	Long userId = jwtUtlity.verify(token);
	Optional<UserModel> userDetails = userRepositorty.findById(userId);
	Optional<NoteModel> notes = null;
	List<NoteModel> notesList = null;
	if (userDetails.isPresent()) {
	if (userDetails.get().isVerify()) {
	notesList = userDetails.get().getNote();
	notesList = notesList.stream().filter(notecheck -> notecheck.getRemindMe() != null)
	.collect(Collectors.toList());
	} else
	throw new CustomException(104, "Invalid token or user not verified");
	} else
	throw new CustomException(102, "User not exist");
	return notesList;
	}
	
	@Override
	public List<NoteModel> getArchivedNotes(String token) {
	Long userId = jwtUtlity.verify(token);
	Optional<UserModel> userDetails = userRepositorty.findById(userId);
	Optional<NoteModel> notes = null;
	List<NoteModel> notesList = null;
	if (userDetails.isPresent()) {
	if (userDetails.get().isVerify()) {
	notesList = userDetails.get().getNote();
	notesList = notesList.stream().filter(notecheck -> notecheck.isArchive()).collect(Collectors.toList());
	} else
	throw new CustomException(104, "Invalid token or user not verified");
	} else
	throw new CustomException(102, "User not exist");
	return notesList;
	}

	@Override
	public List<NoteModel> getTrash(String token) {
	Long userId = jwtUtlity.verify(token);
	Optional<UserModel> userDetails = userRepositorty.findById(userId);
	Optional<NoteModel> notes = null;
	List<NoteModel> notesList = null;
	if (userDetails.isPresent()) {
	if (userDetails.get().isVerify()) {
	notesList = userDetails.get().getNote();
	notesList = notesList.stream().filter(notecheck -> notecheck.isInTrash()).collect(Collectors.toList());
	} else
	throw new CustomException(104, "Invalid token or user not verified");
	} else
	throw new CustomException(102, "User not exist");
	return notesList;
	}

	@Override
	public NoteModel deleteNode(Long noteId, String token) {
	Long userId = jwtUtlity.verify(token);
	Optional<UserModel> userDetails = userRepositorty.findById(userId);
	Optional<NoteModel> notes = null;
	if (userDetails.isPresent() && noteId != null) {
	notes = noteRepository.findById(noteId);
	if (notes.isPresent() && userDetails.get().isVerify()) {
	noteRepository.deleteById(noteId);
	} else
	throw new CustomException(104, "Invalid token");
	}
	return notes.get();
	}

	@Override
	public String deleteRemainder(Long noteId, String token) {
		Long userId = jwtUtlity.verify(token);
		Optional<UserModel> userDetails = userRepositorty.findById(userId);
		Optional<NoteModel> noteModel=null;
		if(userDetails.isPresent()&& userDetails.get().isVerify()) {
			noteModel=noteRepository.findById(noteId);
			if(noteModel.isPresent()&&noteId!=null) {
			noteModel.get().setRemindMe(null);
			noteRepository.save(noteModel.get());
			return "remainder remove";
			}
			else {
				throw new CustomException(103, "Invalid note");
			}
		}
		else {
			throw new CustomException(104, "Invalid token");
		}
	}

	@Override
	public List<NoteModel> getNotesOnLabel(Long labelId, String token) {
		Long userId = jwtUtlity.verify(token);
		List<NoteModel> notes=null;
		Optional<UserModel> userDetails = userRepositorty.findById(userId);
		Optional<LabelModel> labelModel=null;
		if(userDetails.isPresent()&& userDetails.get().isVerify()) {
			labelModel=labelRepository.findById(labelId);
			if(labelModel.isPresent()) {
				 notes=getMultipleNotesOfLabel(labelId);
			}
		}
		return notes;
	}
	private List<NoteModel> getMultipleNotesOfLabel(Long labelId){
		ArrayList<NoteModel> notes1 = new ArrayList<NoteModel>();
		List<Long> noteIds=labelRepository.finddByLabelId(labelId);
		if(!noteIds.isEmpty()) {
		for(Long noteId: noteIds)
		{
			System.out.println(noteId);
			Optional<NoteModel> note = noteRepository.findById(noteId);
			notes1.add(note.get());
		}
		}
		return notes1;
	}

	@Override
	public List<NoteModel> fetchNotesFromArchive(String token) {
		Long userId = jwtUtlity.verify(token);
		Optional<UserModel> userModel=userRepositorty.findById(userId);
		List<NoteModel> notesList=null;
		if(userModel.isPresent()) {
			notesList = notesList.stream().filter(notecheck -> !notecheck.isArchive()).collect(Collectors.toList());
		}
		return notesList;
	}

	

	
	}
	

