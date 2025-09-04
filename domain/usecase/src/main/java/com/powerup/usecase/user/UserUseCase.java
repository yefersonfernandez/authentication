package com.powerup.usecase.user;

import com.powerup.enums.ExceptionMessages;
import com.powerup.exception.EmailAlreadyExistsException;
import com.powerup.exception.IdentityDocumentNotFoundException;
import com.powerup.model.user.User;
import com.powerup.model.user.gateways.IPasswordEncoderPort;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static com.powerup.usecase.util.UserUtils.validateBaseSalary;

@RequiredArgsConstructor
public class UserUseCase {

    private final IUserRepositoryPort userRepository;
    private final IPasswordEncoderPort passwordEncoderPort;

    public Mono<User> saveUser(User user) {
        return validateEmail(user.getEmail())
                .then(validateBaseSalary(user.getBaseSalary()))
                .thenReturn(user)
                .map(this::encodePassword)
                .flatMap(userRepository::saveUser);
    }

    public Mono<User> findUserByIdentityDocument(String identityDocument) {
        return userRepository.findUserByIdentityDocument(identityDocument)
                .switchIfEmpty( Mono.error(() -> new IdentityDocumentNotFoundException(ExceptionMessages.USER_NOT_FOUND.format(identityDocument))));
    }

    private Mono<Void> validateEmail(String email) {
        return userRepository.existsByEmail(email)
                .flatMap(exists -> exists
                                ? Mono.error(new EmailAlreadyExistsException(
                                ExceptionMessages.USER_WITH_EMAIL_EXISTS.format(email)
                        ))
                                : Mono.empty()
                );
    }

    private User encodePassword(User user) {
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        return user;
    }
}
