package com.powerup.api;

import com.powerup.api.dto.error.CustomError;
import com.powerup.api.dto.request.UserRequestDto;
import com.powerup.api.dto.response.UserResponseDto;
import com.powerup.api.mapper.IUserMapper;
import com.powerup.api.util.ValidatorUtil;
import com.powerup.usecase.user.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {
    private final UserUseCase userUseCase;
    private final IUserMapper userMapper;
    private final ValidatorUtil validatorUtil;

    @Operation(
            operationId = "saveUser",
            summary = "Register a new user",
            description = "Registers a new user after validating unique email and salary range",
            requestBody = @RequestBody(
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request (e.g., validation errors or salary out of range)",
                            content = @Content(schema = @Schema(implementation = CustomError.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict (e.g., user with the given email already exists)",
                            content = @Content(schema = @Schema(implementation = CustomError.class))
                    )
            }
    )
    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDto.class)
                .flatMap(validatorUtil::validate)
                .map(userMapper::toModel)
                .doOnNext(user -> log.debug("Received user request: {}", user))
                .flatMap(userUseCase::saveUser)
                .doOnSuccess(savedUser -> log.info("User saved successfully with email={}", savedUser.getEmail()))
                .doOnError(error -> log.error("Error while saving user: {}", error.getMessage(), error))
                .map(userMapper::toUserResponseDto)
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser));
    }

    @Operation(
            operationId = "findUserByIdentityDocument",
            summary = "Find a user by Identity Document",
            description = "Retrieves a user from the system using their identity document. Returns the user information if found.",
            parameters = {
                    @Parameter(
                            name = "identityDocument",
                            description = "Identity document of the user to search",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User found successfully",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request (e.g., missing or malformed identity document)",
                            content = @Content(schema = @Schema(implementation = CustomError.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found with the given identity document",
                            content = @Content(schema = @Schema(implementation = CustomError.class))
                    )
            }
    )
    public Mono<ServerResponse> listenFindUserByIdentityDocument(ServerRequest serverRequest) {
        String identityDocument = serverRequest.pathVariable("identityDocument");
        return userUseCase.findUserByIdentityDocument(identityDocument)
                .flatMap(user ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user)
                );
    }
}
