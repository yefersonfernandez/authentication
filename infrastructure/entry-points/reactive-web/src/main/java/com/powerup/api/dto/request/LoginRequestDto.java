package com.powerup.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO for user login")
public record LoginRequestDto(

        @Email(message = "Email must have a valid format")
        @NotBlank(message = "Email cannot be blank")
        @Schema(description = "User's email address", example = "andres@gmail.com")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Schema(description = "User's password", example = "MySecret123")
        String password

) {}