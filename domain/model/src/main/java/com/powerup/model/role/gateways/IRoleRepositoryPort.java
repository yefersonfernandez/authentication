package com.powerup.model.role.gateways;

import com.powerup.model.role.Role;
import reactor.core.publisher.Mono;

public interface IRoleRepositoryPort {
    Mono<Role> findById(Long id);
}
