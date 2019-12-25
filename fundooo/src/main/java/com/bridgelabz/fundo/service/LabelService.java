package com.bridgelabz.fundo.service;

import java.util.List;

import com.bridgelabz.fundo.dto.LabelDTO;
import com.bridgelabz.fundo.dto.NoteDTO;
import com.bridgelabz.fundo.model.LabelModel;
import com.bridgelabz.fundo.model.NoteModel;

public interface LabelService {
	public LabelModel createLabel(String token, LabelDTO labelDto);
	public String updateLabel(String token, Long id,LabelDTO labelDto);
	public String deleteLabels(String token,Long id);
	public List<LabelModel> retrieveAllLabel(String token);
}
