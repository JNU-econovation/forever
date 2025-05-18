package com.example.forever.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Folder extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 폴더 이름
    @Column(nullable = false, length = 255)
    private String name;

//    // 상위 폴더 (null이면 루트 폴더)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_folder_id")
//    private Folder parentFolder;

//    // 하위 폴더 리스트 (양방향 연관 매핑)
//    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Folder> children = new ArrayList<>();

    @Column(nullable = false)
    private Long createdBy; // 생성자 ID 추적용
    
    @Builder.Default
    @Column(nullable = false)
    private boolean isDeleted = false;
    
    private LocalDateTime deletedAt;

    public void updateName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    
    public void restore() {
        this.isDeleted = false;
        this.deletedAt = null;
    }

    // 특정 멤버가 소유자인지 여부 검사
    public boolean isOwnedBy(Long memberId) {
        return this.createdBy.equals(memberId);
    }
}
