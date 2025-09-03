package com.powerup.r2dbc.user;

import com.powerup.model.user.User;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import com.powerup.r2dbc.entity.UserEntity;
import com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, Long, IUserRepository> implements IUserRepositoryPort {
    public UserRepositoryAdapter(IUserRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class), transactionalOperator);
    }

    @Override
    public Mono<User> saveUser(User user) {
        return super.save(user);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<User> findUserByIdentityDocument(String identityDocument) {
        return repository.findByIdentityDocument(identityDocument);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
