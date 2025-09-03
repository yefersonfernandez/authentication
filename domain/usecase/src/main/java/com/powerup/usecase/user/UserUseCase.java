package com.powerup.usecase.user;

import com.powerup.enums.ExceptionMessages;
import com.powerup.exception.EmailAlreadyExistsException;
import com.powerup.exception.IdentityDocumentNotFoundException;
import com.powerup.exception.InvalidSalaryRangeException;
import com.powerup.model.user.User;
import com.powerup.model.user.gateways.IPasswordEncoderPort;
import com.powerup.model.user.gateways.IUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

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

    private Mono<Void> validateBaseSalary(BigDecimal baseSalary) {
        BigDecimal minSalary = BigDecimal.ZERO;
        BigDecimal maxSalary = BigDecimal.valueOf(15_000_000);

        if (baseSalary.compareTo(minSalary) < 0 || baseSalary.compareTo(maxSalary) > 0) {
            return Mono.error(new InvalidSalaryRangeException(
                    ExceptionMessages.BASE_SALARY_OUT_OF_RANGE.format(baseSalary)
            ));
        }
        return Mono.empty();
    }

    private User encodePassword(User user) {
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        return user;
    }
}
