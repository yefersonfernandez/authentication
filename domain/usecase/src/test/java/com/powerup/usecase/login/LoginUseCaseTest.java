package com.powerup.usecase.login;

import com.powerup.exception.InvalidCredentialsException;
import com.powerup.model.role.Role;
import com.powerup.model.role.gateways.IRoleRepositoryPort;
import com.powerup.model.token.Token;
import com.powerup.model.token.gateways.ITokenProviderPort;
import com.powerup.model.user.User;
import com.powerup.model.user.gateways.IPasswordEncoderPort;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private IUserRepositoryPort userRepositoryPort;

    @Mock
    private IRoleRepositoryPort roleRepositoryPort;

    @Mock
    private ITokenProviderPort tokenProviderPort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private User user;
    private Role role;
    private Token token;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Admin")
                .build();

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
                .roleId(role.getId())
                .build();

        token = Token.builder()
                .accessToken("token123")
                .build();
    }

    @Test
    @DisplayName("Must login successfully with valid credentials")
    void testLoginSuccess() {
        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(passwordEncoderPort.matches(anyString(), anyString())).thenReturn(true);
        when(roleRepositoryPort.findById(user.getRoleId())).thenReturn(Mono.just(role));
        when(tokenProviderPort.createToken(user.getEmail(), role.getName())).thenReturn(Mono.just(token));

        StepVerifier.create(loginUseCase.login(user.getEmail(), user.getPassword()))
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    @DisplayName("Must return error if user not found")
    void testLoginUserNotFound() {
        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Mono.empty());

        StepVerifier.create(loginUseCase.login(user.getEmail(), user.getPassword()))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }

    @Test
    @DisplayName("Must return error if password is incorrect")
    void testLoginInvalidPassword() {
        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(passwordEncoderPort.matches(anyString(), anyString())).thenReturn(false);

        StepVerifier.create(loginUseCase.login(user.getEmail(), "wrongPassword"))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }

    @Test
    @DisplayName("Must return error if role not found")
    void testLoginRoleNotFound() {
        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
        when(passwordEncoderPort.matches(anyString(), anyString())).thenReturn(true);
        when(roleRepositoryPort.findById(user.getRoleId())).thenReturn(Mono.empty());

        StepVerifier.create(loginUseCase.login(user.getEmail(), user.getPassword()))
                .expectError(InvalidCredentialsException.class)
                .verify();
    }
}
