package com.powerup.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserResponseDto(

        @Schema(description = "Unique identifier of the user", example = "1")
        Long id,

        @Schema(description = "User's first name", example = "Andres")
        String firstName,

        @Schema(description = "User's last name", example = "Pru")
        String lastName,

        @Schema(description = "User's birth date", example = "1990-01-01")
        LocalDate birthDate,

        @Schema(description = "User's address", example = "Calle 123")
        String address,

        @Schema(description = "User's phone number", example = "3133131111")
        String phone,

        @Schema(description = "User's identity document", example = "1234567890")
        String identityDocument,

        @Schema(description = "User's email address", example = "andres@gmail.com")
        String email,

        @Schema(description = "User's base salary", example = "50000")
        BigDecimal baseSalary
) {}
