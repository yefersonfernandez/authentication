package com.powerup.api.login;

import com.powerup.api.config.ApiPaths;
import com.powerup.api.openapi.LoginOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
@RequiredArgsConstructor
public class LoginRouterRest {

    private final ApiPaths apiPaths;

    @Bean
    public RouterFunction<ServerResponse> loginRouterFunction(LoginHandler handler) {
        return route()
                .POST(apiPaths.getLogin(), handler::listenLogin, LoginOpenApi::login)
                .build();
    }
}






