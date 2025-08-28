package com.powerup.model.user.gateways;

import com.powerup.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserRepositoryPort {
    Mono<User> saveUser(User user);
    Mono<Boolean> existsByEmail(String email);
    Mono<User> findUserByIdentityDocument(String identityDocument);
}
