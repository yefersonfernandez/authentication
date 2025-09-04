package com.powerup.usecase.util;

import com.powerup.enums.ExceptionMessages;
import com.powerup.exception.InvalidCredentialsException;
import reactor.core.publisher.Mono;

public class LoginUtils {
    private LoginUtils() {
    }

    public static <T> Mono<T> invalidCredentials() {
        return Mono.error(new InvalidCredentialsException(
                ExceptionMessages.INVALID_CREDENTIALS.getMessage()
        ));
    }
}
