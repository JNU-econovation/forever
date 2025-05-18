package com.example.forever.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback_tb", indexes = {
    @Index(name = "idx_feedback_position", columnList = "position"),
    @Index(name = "idx_feedback_processed", columnList = "processed")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 피드백에 대한 처리 여부 (관리자가 확인했는지)
    @Builder.Default
    private boolean processed = false;
    
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
