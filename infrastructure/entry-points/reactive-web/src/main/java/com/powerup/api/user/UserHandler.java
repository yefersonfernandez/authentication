package com.powerup.api.user;

import com.powerup.api.dto.request.UserRequestDto;
import com.powerup.api.mapper.IUserMapper;
import com.powerup.api.util.ValidatorUtil;
import com.powerup.usecase.user.UserUseCase;
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

    public Mono<ServerResponse> listenFindUserByIdentityDocument(ServerRequest serverRequest) {
        String identityDocument = serverRequest.pathVariable("identityDocument");
        return userUseCase.findUserByIdentityDocument(identityDocument)
                .flatMap(user ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user)
                );
    }

    public Mono<ServerResponse> listenFindUserByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
        return userUseCase.findUserByEmail(email)
                .flatMap(user ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user)
                );
    }
}
