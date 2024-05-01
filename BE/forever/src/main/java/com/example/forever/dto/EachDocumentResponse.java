package com.example.forever.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EachDocumentResponse {
    private Long documentId;
    private String title;

}
