package com.powerup.exception;

import com.powerup.enums.ExceptionStatusCode;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException( String message) {
        super(ExceptionStatusCode.CONFLICT, message);
    }
}
