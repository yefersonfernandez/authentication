package com.powerup.model.token.gateways;

import com.powerup.model.token.Token;
import reactor.core.publisher.Mono;

public interface ITokenProviderPort {
    Mono<Token> createToken(String email, String role);
}
