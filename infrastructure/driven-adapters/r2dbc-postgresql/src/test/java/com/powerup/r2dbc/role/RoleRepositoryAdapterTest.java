package com.powerup.r2dbc.role;

import com.powerup.model.role.Role;
import com.powerup.r2dbc.entity.RoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryAdapterTest {

    @Mock
    private IRoleRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TransactionalOperator transactionalOperator;

    @InjectMocks
    private RoleRepositoryAdapter roleRepositoryAdapter;

    private Role role;
    private RoleEntity roleEntity;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Administrator role")
                .build();

        roleEntity = RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();

        lenient().when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Should find role by id and return domain object")
    void testFindByIdSuccess() {
        when(repository.findById(role.getId())).thenReturn(Mono.just(roleEntity));
        when(mapper.map(roleEntity, Role.class)).thenReturn(role);

        StepVerifier.create(roleRepositoryAdapter.findById(role.getId()))
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when role id not found")
    void testFindByIdNotFound() {
        when(repository.findById(role.getId())).thenReturn(Mono.empty());

        StepVerifier.create(roleRepositoryAdapter.findById(role.getId()))
                .verifyComplete();
    }
}
