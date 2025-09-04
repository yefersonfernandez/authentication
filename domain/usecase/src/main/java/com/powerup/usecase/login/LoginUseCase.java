package com.powerup.usecase.login;


import com.powerup.model.role.gateways.IRoleRepositoryPort;
import com.powerup.model.token.Token;
import com.powerup.model.token.gateways.ITokenProviderPort;
import com.powerup.model.user.gateways.IPasswordEncoderPort;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static com.powerup.usecase.util.LoginUtils.invalidCredentials;

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
}
