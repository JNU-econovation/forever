package com.example.forever.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 포함된 폴더 (null이면 루트)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Column(nullable = false)
    private boolean isDeleted = false;
    
    private LocalDateTime deletedAt;

    @Builder
    public Document(String title, String summary, Member member, Folder folder) {
        this.title = title;
        this.summary = summary;
        this.member = member;
        this.folder = folder;
        this.isDeleted = false;
    }

    @Builder
    public Document(Long id, String title, String summary, Member member, Folder folder) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.member = member;
        this.folder = folder;
        this.isDeleted = false;
    }

    public void update(String title) {
        this.title = title;
    }
    
    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    
    public void restore() {
        this.isDeleted = false;
        this.deletedAt = null;
    }
}