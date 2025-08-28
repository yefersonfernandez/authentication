package com.powerup.api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;



@Schema(description = "Request DTO for creating a user")
public record UserRequestDto(

        @NotBlank(message = "First name cannot be blank")
        @Schema(description = "User's first name", example = "Andres")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Schema(description = "User's last name", example = "Pru")
        String lastName,

        @NotNull(message = "Birth date cannot be null")
        @Schema(description = "User's birth date", example = "1990-01-01")
        LocalDate birthDate,

        @NotBlank(message = "Address cannot be blank")
        @Schema(description = "User's address", example = "cll 123 #45")
        String address,

        @NotBlank(message = "Phone cannot be blank")
        @Schema(description = "User's phone number", example = "3133131111")
        String phone,

        @NotBlank(message = "Identity Document cannot be blank")
        @Schema(description = "User's identity document", example = "1234567890")
        String identityDocument,

        @Email(message = "Email must have a valid format")
        @NotBlank(message = "Email cannot be blank")
        @Schema(description = "User's email address", example = "andres@gmail.com")
        String email,

        @NotNull(message = "Base salary cannot be null")
        @Schema(description = "User's base salary", example = "50000")
        BigDecimal baseSalary

) {}
