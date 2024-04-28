package com.example.forever.domain;

import com.example.forever.domain.Document;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="question_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Builder
    public Question(String content, Document document) {
        this.content = content;
        this.document = document;
    }
}

