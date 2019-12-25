package com.bridgelabz.fundo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.ForgetPaswordDTO;
import com.bridgelabz.fundo.dto.ResetPasswordDTO;
import com.bridgelabz.fundo.dto.UserDTO;
import com.bridgelabz.fundo.dto.UserLoginDTO;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.model.Response;
import com.bridgelabz.fundo.model.UserModel;
import com.bridgelabz.fundo.service.NoteServiceImplementation;
import com.bridgelabz.fundo.service.UserServiceImplementation;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

	@Autowired
	private UserServiceImplementation userServiceImplementation;
	@Autowired
	private NoteServiceImplementation noteServiceImplementation;

	@PostMapping("/registration")
	public ResponseEntity<UserModel> userRegistration(@RequestBody UserDTO userDto) {
		System.out.println("inside register");
		UserModel userModel = userServiceImplementation.userRegistration(userDto);
		return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
	}

	@GetMapping("/verification/{token}")
	public ResponseEntity<String> verification(@PathVariable String token) {
		String tok = userServiceImplementation.verification(token);
		return new ResponseEntity<String>(tok, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody UserLoginDTO userLoginDto) {
		String tok = userServiceImplementation.login(userLoginDto);
		return  ResponseEntity.status(HttpStatus.OK).body(new Response(tok, HttpStatus.OK.value()));
	}

	@PostMapping("/forget")
	public ResponseEntity<Response> forgetPassword(@RequestBody ForgetPaswordDTO forgetPasswordDto) {
		String forget = userServiceImplementation.forgetPassword(forgetPasswordDto.getEmail());
		return ResponseEntity.status(HttpStatus.OK).body(new Response(forget,HttpStatus.OK.value()));
	}

	@PostMapping("/reset")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDto,
			@RequestHeader String token) {
		System.out.println("reched inside reset merthid");
		String reset = userServiceImplementation.resetPassword(resetPasswordDto, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(reset,HttpStatus.OK.value()));
	}
	
	@PostMapping("/addcollaborator")
	public ResponseEntity<LabelModel> addCollaborator(@RequestHeader String token, @RequestParam String email,
	@RequestParam Long noteId) {
		noteServiceImplementation.addCollaborater(noteId, email, token);
	return new ResponseEntity<LabelModel>(HttpStatus.OK);
	}

	@DeleteMapping("/removecollaborator")
	public ResponseEntity<LabelModel> removeCollaborator(@RequestHeader String token, @RequestParam String email,
	@RequestParam Long noteId) {
		noteServiceImplementation.removeCollaborater(token, email, noteId);
	return new ResponseEntity<LabelModel>(HttpStatus.OK);
	}

}
