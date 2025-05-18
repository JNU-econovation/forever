package com.example.forever.repository;

import com.example.forever.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByPositionOrderByCreatedAtDesc(String position);
    List<Feedback> findByProcessedOrderByCreatedAtDesc(boolean processed);
}
