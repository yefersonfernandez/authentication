package com.powerup.exception;

import com.powerup.enums.ExceptionStatusCode;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException(String message) {
        super(ExceptionStatusCode.UNAUTHORIZED, message);
    }
}
