package com.powerup.r2dbc;

import com.powerup.exception.IdentityDocumentNotFoundException;
import com.powerup.model.user.User;
import com.powerup.r2dbc.entity.UserEntity;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TransactionalOperator transactionalOperator;

    @InjectMocks
    private UserRepositoryAdapter repositoryAdapter;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("Andres")
                .lastName("Pru")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("cll")
                .phone("312009212")
                .identityDocument("123")
                .email("andres@gmail.com")
                .baseSalary(new BigDecimal(5000))
                .build();

        userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setBirthDate(user.getBirthDate());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhone(user.getPhone());
        userEntity.setIdentityDocument(user.getIdentityDocument());
        userEntity.setEmail(user.getEmail());
        userEntity.setBaseSalary(user.getBaseSalary());

        lenient().when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("Should save a user and return domain object")
    void testSaveUser() {
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        StepVerifier.create(repositoryAdapter.saveUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return true if email exists")
    void testExistsByEmailTrue() {
        when(repository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));

        StepVerifier.create(repositoryAdapter.existsByEmail(user.getEmail()))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return false if email does not exist")
    void testExistsByEmailFalse() {
        when(repository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));

        StepVerifier.create(repositoryAdapter.existsByEmail(user.getEmail()))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return user when identity document exists")
    void testFindUserByIdentityDocumentSuccess() {
        when(repository.findByIdentityDocument(user.getIdentityDocument()))
                .thenReturn(Mono.just(user));

        StepVerifier.create(repositoryAdapter.findUserByIdentityDocument(user.getIdentityDocument()))
                .expectNext(user)
                .verifyComplete();
    }
}
