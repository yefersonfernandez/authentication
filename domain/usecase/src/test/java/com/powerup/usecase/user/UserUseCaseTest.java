package com.powerup.usecase.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.powerup.exception.IdentityDocumentNotFoundException;
import com.powerup.model.user.gateways.IPasswordEncoderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.powerup.exception.EmailAlreadyExistsException;
import com.powerup.exception.InvalidSalaryRangeException;
import com.powerup.model.user.User;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserRepositoryPort userRepository;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

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
                .password("123")
                .build();
    }

    @Test
    @DisplayName("Must save a user successfully")
    void testSaveUser() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));
        when(passwordEncoderPort.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.saveUser(user)).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user).log())
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Must return error if email already exists")
    void testSaveUserWithEmailAlreadyExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(EmailAlreadyExistsException.class)
                .verify();
    }

    @Test
    @DisplayName("Must return error if baseSalary is below minimum")
    void testSaveUserWithBaseSalaryTooLow() {
        user.setBaseSalary(new BigDecimal(-100));

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(InvalidSalaryRangeException.class)
                .verify();
    }

    @Test
    @DisplayName("Must return error if baseSalary exceeds maximum")
    void testSaveUserWithBaseSalaryTooHigh() {
        user.setBaseSalary(new BigDecimal(20_000_000));

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(InvalidSalaryRangeException.class)
                .verify();
    }

    @Test
    @DisplayName("Must return user when identity document exists")
    void testFindUserByIdentityDocumentSuccess() {
        user.setIdentityDocument("1111111111");

        when(userRepository.findUserByIdentityDocument(user.getIdentityDocument()))
                .thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.findUserByIdentityDocument(user.getIdentityDocument()))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    @DisplayName("Must return error when identity document does not exist")
    void testFindUserByIdentityDocumentNotFound() {
        user.setIdentityDocument("1234567890");

        when(userRepository.findUserByIdentityDocument("1234567890"))
                .thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.findUserByIdentityDocument(user.getIdentityDocument()))
                .expectError(IdentityDocumentNotFoundException.class)
                .verify();
    }

}