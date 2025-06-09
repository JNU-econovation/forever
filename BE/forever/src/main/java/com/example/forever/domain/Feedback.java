package com.example.forever.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, length = 100)
    private String position;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FeedbackContent> contents = new ArrayList<>();

    // 피드백에 대한 처리 여부 (관리자가 확인했는지)
    @Builder.Default
    private boolean processed = false;
    
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * 피드백 내용 추가
     */
    public void addContent(String content) {
        FeedbackContent feedbackContent = FeedbackContent.create(this, content);
        this.contents.add(feedbackContent);
    }

    /**
     * 여러 피드백 내용 추가
     */
    public void addContents(List<String> contentList) {
        for (String content : contentList) {
            addContent(content);
        }
    }

    /**
     * 피드백 생성 팩토리 메서드
     */
    public static Feedback create(String position, Integer rating, Member member, List<String> contents) {
        Feedback feedback = Feedback.builder()
                .position(position)
                .rating(rating)
                .member(member)
                .build();
        
        feedback.addContents(contents);
        return feedback;
    }
}
