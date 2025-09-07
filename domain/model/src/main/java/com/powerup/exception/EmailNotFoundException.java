package com.powerup.exception;

import com.powerup.enums.ExceptionStatusCode;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException(String message) {
        super(ExceptionStatusCode.NOT_FOUND, message);
    }
}
