package com.powerup.exception;

import com.powerup.enums.ExceptionStatusCode;

public class IdentityDocumentNotFoundException extends BusinessException {

    public IdentityDocumentNotFoundException(String message) {
        super(ExceptionStatusCode.NOT_FOUND, message);
    }
}
