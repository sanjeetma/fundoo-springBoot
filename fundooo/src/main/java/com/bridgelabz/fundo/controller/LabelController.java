package com.bridgelabz.fundo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.dto.LabelDTO;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.service.LabelImplementation;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/label")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "jwtToken" })
public class LabelController {

	@Autowired
	private LabelImplementation labelImplemetation;
	
	@PostMapping("/createLabel")
	public LabelModel createLabel(@RequestHeader String token, @RequestBody LabelDTO labelDto) {
		return labelImplemetation.createLabel(token, labelDto);
	}
	@PutMapping("/updateLabel")
	public ResponseEntity<Response> updateLabel(@RequestHeader String token, @RequestParam Long id, @RequestBody LabelDTO labelDto) {
		String message=labelImplemetation.updateLabel(token, id, labelDto);
		Response response=new Response(HttpStatus.ACCEPTED.value(), message);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@DeleteMapping("/deleteLabel")
	public ResponseEntity<Response> deleteLabels(@RequestParam Long id,@RequestHeader String token) {
		String message=labelImplemetation.deleteLabels(token, id);
		Response response=new Response(HttpStatus.ACCEPTED.value(), message);
		return new ResponseEntity<Response>(response,HttpStatus.OK);

	}
	@GetMapping("/retrieveAllLavels")
	public List<LabelModel> retrieveAllNode(@RequestHeader String token){
		System.err.println(token);
		return labelImplemetation.retrieveAllLabel(token);
	}
}
