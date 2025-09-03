package com.powerup.usecase.login;

import com.powerup.enums.ExceptionMessages;
import com.powerup.exception.InvalidCredentialsException;
import com.powerup.model.role.gateways.IRoleRepositoryPort;
import com.powerup.model.token.Token;
import com.powerup.model.token.gateways.ITokenProviderPort;
import com.powerup.model.user.gateways.IPasswordEncoderPort;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    private final IUserRepositoryPort userRepositoryPort;
    private final IRoleRepositoryPort roleRepositoryPort;
    private final ITokenProviderPort tokenProviderPort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public Mono<Token> login(String email, String password) {
        return userRepositoryPort.findByEmail(email)
                .switchIfEmpty(invalidCredentials())
                .filter(user -> passwordEncoderPort.matches(password, user.getPassword()))
                .switchIfEmpty(invalidCredentials())
                .flatMap(user -> roleRepositoryPort.findById(user.getRoleId())
                        .switchIfEmpty(invalidCredentials())
                        .flatMap(role -> tokenProviderPort.createToken(user.getEmail(), role.getName()))
                );
    }

    private <T> Mono<T> invalidCredentials() {
        return Mono.error(new InvalidCredentialsException(ExceptionMessages.INVALID_CREDENTIALS.getMessage()));
    }
}
