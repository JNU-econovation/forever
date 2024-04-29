package com.example.forever.repository;

import com.example.forever.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.document.id = :documentId")
    List<Question> findAllByDocumentId(Long documentId);
}
