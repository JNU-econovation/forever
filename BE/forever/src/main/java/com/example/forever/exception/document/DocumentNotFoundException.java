package com.example.forever.exception.document;

import com.example.forever.common.errorcode.DocumentErrorCode;
import com.example.forever.common.exception.DocumentException;

public class DocumentNotFoundException extends DocumentException {
    public DocumentNotFoundException() {
        super(DocumentErrorCode.DOCUMENT_NOT_FOUND);
    }
}

