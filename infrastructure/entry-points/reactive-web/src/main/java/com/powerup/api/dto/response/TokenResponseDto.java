package com.powerup.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing the access token")
public record TokenResponseDto(

        @Schema(description = "JWT access token", example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken

) {}
