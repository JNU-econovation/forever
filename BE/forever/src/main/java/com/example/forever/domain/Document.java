package com.example.forever.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="document_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(columnDefinition="TEXT")
    private String summary;

}
