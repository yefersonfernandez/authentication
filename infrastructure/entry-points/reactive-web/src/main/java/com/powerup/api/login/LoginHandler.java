package com.powerup.api.login;

import com.powerup.api.dto.request.LoginRequestDto;
import com.powerup.api.mapper.ITokenMapper;
import com.powerup.api.util.ValidatorUtil;
import com.powerup.usecase.login.LoginUseCase;
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
public class LoginHandler {
    private final LoginUseCase loginUseCase;
    private final ITokenMapper tokenMapper;
    private final ValidatorUtil validatorUtil;


    public Mono<ServerResponse> listenLogin(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginRequestDto.class)
                .flatMap(validatorUtil::validate)
                .doOnNext(req -> log.debug("Received login request: {}", req))
                .flatMap(loginRequest -> loginUseCase.login(loginRequest.email(), loginRequest.password()))
                .map(tokenMapper::toTokenResponseDto)
                .doOnNext(tokenResponse -> log.info("Login successful for email={}", tokenResponse.accessToken()))
                .flatMap(tokenResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tokenResponse))
                .doOnError(error -> log.error("Error during login: {}", error.getMessage(), error));
    }}
