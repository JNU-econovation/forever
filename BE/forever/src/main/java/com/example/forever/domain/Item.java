package com.example.forever.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FILE or FOLDER
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType type;

    // File 또는 Folder 테이블의 실제 참조 ID
    @Column(name = "ref_id", nullable = false)
    private Long refId;

    // 어떤 폴더 안에 속해 있는가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id") // Folder의 ID
    private Folder folder;

    // 순서 값
    @Column(name = "order_value", nullable = false)
    private int orderValue;

    @Builder
    public Item(ItemType type, Long refId, Folder folder, int orderValue) {
        this.type = type;
        this.refId = refId;
        this.folder = folder;
        this.orderValue = orderValue;
    }

       public void updateOrder(int newOrder) {
           this.orderValue = newOrder;
    }

    public void moveTo(Folder newFolder, int newOrder) {
        this.folder = newFolder;
        this.orderValue = newOrder;
    }
}