package com.example.forever.exception.folder;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.errorcode.FolderErrorCode;
import com.example.forever.common.exception.AuthException;
import com.example.forever.common.exception.FolderException;

public class FolderNotFoundException extends FolderException {
    public FolderNotFoundException() {
        super(FolderErrorCode.FOLDER_NOT_FOUND);
    }
}