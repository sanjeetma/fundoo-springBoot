package com.bridgelabz.fundo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundo.model.LabelModel;

public interface LabelRepository extends JpaRepository<LabelModel, Long>{
@Query(value="select note_model_note_id from note_model_label_model where label_model_label_id=?1" , nativeQuery=true)
List<Long> finddByLabelId(Long labelId);
}
