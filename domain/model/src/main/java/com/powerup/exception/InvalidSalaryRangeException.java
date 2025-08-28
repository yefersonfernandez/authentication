package com.powerup.exception;

import com.powerup.enums.ExceptionStatusCode;

public class InvalidSalaryRangeException extends BusinessException {

    public InvalidSalaryRangeException(String message) {
        super(ExceptionStatusCode.BAD_REQUEST, message);
    }
}
