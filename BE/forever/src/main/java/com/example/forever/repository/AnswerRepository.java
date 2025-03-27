package com.example.forever.repository;

import com.example.forever.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a JOIN FETCH  a.question q WHERE q.id = :questionId")
    Optional<Answer> findByQuestionId(Long questionId);

}
