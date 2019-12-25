package com.bridgelabz.fundo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundo.model.NoteModel;
@Repository
public interface NoteRepository extends JpaRepository<NoteModel, Long> {

	@Query(value = "SELECT collabrator_note_list_note_id FROM note_model_collabrator_user_list WHERE collabrator_user_list_user_id = ?1", nativeQuery = true)
	List<Long> findBycollabratorUserList(Long id);

	@Query(value = "SELECT collabrator_user_list_user_id FROM note_model_collabrator_user_list WHERE  collabrator_note_list_note_id = ?1", nativeQuery = true)
	List<Long> findBycollabratorNoteId(Long id); 	
}
