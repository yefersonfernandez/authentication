package com.powerup.r2dbc.role;

import com.powerup.model.role.Role;
import com.powerup.model.role.gateways.IRoleRepositoryPort;
import com.powerup.model.user.User;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import com.powerup.r2dbc.entity.RoleEntity;
import com.powerup.r2dbc.entity.UserEntity;
import com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class RoleRepositoryAdapter extends ReactiveAdapterOperations<Role, RoleEntity, Long, IRoleRepository> implements IRoleRepositoryPort {
    public RoleRepositoryAdapter(IRoleRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Role.class), transactionalOperator);
    }
}
