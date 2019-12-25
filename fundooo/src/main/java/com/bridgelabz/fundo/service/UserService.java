package com.bridgelabz.fundo.service;

import com.bridgelabz.fundo.dto.NoteDTO;
import com.bridgelabz.fundo.dto.ResetPasswordDTO;
import com.bridgelabz.fundo.dto.UserDTO;
import com.bridgelabz.fundo.dto.UserLoginDTO;
import com.bridgelabz.fundo.model.NoteModel;
import com.bridgelabz.fundo.model.UserModel;

public interface UserService {
	public UserModel userRegistration(UserDTO userDto);

	public String verification(String email);

	public String login(UserLoginDTO userLoginDto) throws Exception;
    
	public String forgetPassword(String email);
	
	String resetPassword(ResetPasswordDTO resetPasswordDto,String token);
	
	NoteModel createNote(String token, NoteDTO noteDto);

}