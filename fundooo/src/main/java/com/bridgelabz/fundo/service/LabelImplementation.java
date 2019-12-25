package com.bridgelabz.fundo.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.dto.LabelDTO;
import com.bridgelabz.fundo.exception.CustomException;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.model.NoteModel;
import com.bridgelabz.fundo.model.UserModel;
import com.bridgelabz.fundo.repository.LabelRepository;
import com.bridgelabz.fundo.repository.NoteRepository;
import com.bridgelabz.fundo.repository.UserRepository;
import com.bridgelabz.fundo.util.JWTUtility;

@Service
public class LabelImplementation implements LabelService{
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LabelRepository labelRepository;
	
	//@Autowired
	//private NoteRepository noteRepositoy;

	@Override
	public LabelModel createLabel(String token, LabelDTO labelDto) {
		System.err.println("in create label");
		Long verifiedId=jwtUtility.verify(token);
		Optional<UserModel> userModel=userRepository.findById(verifiedId);
		LabelModel labelMobel=modelMapper.map(labelDto,LabelModel.class);
		if(userModel.isPresent()) {
			userModel.get().getLabel().add(labelMobel);
			labelMobel=labelRepository.save(labelMobel);
		}
		return labelMobel;
	}

	@Override
	public String updateLabel(String token, Long id, LabelDTO labelDto) {
		String message=null;
		Long verifiedId=jwtUtility.verify(token);
		Optional<UserModel> userModel=userRepository.findById(verifiedId);
		Optional<LabelModel> labelModel=labelRepository.findById(id);
		//Optional<NoteModel> noteModel=noteRepositoy.findById(id);
		if(userModel.isPresent()) {
			if(labelModel.isPresent()&& labelDto.getLabel()!=null) {
				//verify usermodel
				labelModel.get().setLabel(labelDto.getLabel());
				
				//userModel.get().getNote().add(noteModel);
				labelRepository.save(labelModel.get());
				message="Label is update";
			}
			else {
				throw new CustomException(HttpStatus.NOT_ACCEPTABLE.value(), "Label is not exist");
			}
		}
		else {
			throw new CustomException(HttpStatus.NOT_ACCEPTABLE.value(), "User is not exit");
		}
		return message;
	}
	@Override
	public String deleteLabels(String token, Long id) {//same mistake
		String message=null;
		Long verifiedId=jwtUtility.verify(token);
		Optional<UserModel> userModel=userRepository.findById(verifiedId);
		Optional<LabelModel> labelModel=labelRepository.findById(id);
		if (userModel.isPresent()) {
			if(labelModel.isPresent()) {
				labelRepository.delete(labelModel.get());;
                 message="label deleted";
                 System.out.println("label deleted");
			}
			else {
				System.out.println("label is not exit");
				message="label is not exit";
			}
		}
		else {
			System.out.println("user is not exit");
			message="user is not exit";
		}
		return message; 
	}

	@Override
	public List<LabelModel> retrieveAllLabel(String token) {
		Long verifiedUserId = jwtUtility.verify(token);
		List<LabelModel> label=null;
		Optional<UserModel> userModel = userRepository.findById(verifiedUserId);
		if (userModel.isPresent()) 
			label=userModel.get().getLabel();
		return label;
	}

}
