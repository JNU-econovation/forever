package com.example.forever.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback_content_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FeedbackContent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id", nullable = false)
    private Feedback feedback;

    @Column(nullable = false, length = 500)
    private String content;

    /**
     * 피드백 내용 생성 팩토리 메서드
     */
    public static FeedbackContent create(Feedback feedback, String content) {
        return FeedbackContent.builder()
                .feedback(feedback)
                .content(content)
                .build();
    }
}
