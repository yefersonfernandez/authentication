package com.powerup.usecase.util;

import com.powerup.enums.ExceptionMessages;
import com.powerup.exception.InvalidSalaryRangeException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public final class UserUtils {

    private UserUtils() {}

    public static Mono<Void> validateBaseSalary(BigDecimal baseSalary) {
        BigDecimal minSalary = BigDecimal.ZERO;
        BigDecimal maxSalary = BigDecimal.valueOf(15_000_000);

        if (baseSalary.compareTo(minSalary) < 0 || baseSalary.compareTo(maxSalary) > 0) {
            return Mono.error(new InvalidSalaryRangeException(
                    ExceptionMessages.BASE_SALARY_OUT_OF_RANGE.format(baseSalary)
            ));
        }
        return Mono.empty();
    }
}