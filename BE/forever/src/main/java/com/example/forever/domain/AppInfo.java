package com.example.forever.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_info_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latest_version", nullable = false)
    private String latestVersion;

    @Column(name = "store_url", nullable = false)
    private String storeUrl;

    @Builder
    public AppInfo(String latestVersion, String storeUrl) {
        this.latestVersion = latestVersion;
        this.storeUrl = storeUrl;
    }
}

