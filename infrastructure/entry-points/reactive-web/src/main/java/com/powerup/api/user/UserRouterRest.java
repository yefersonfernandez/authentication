package com.powerup.api.user;

import com.powerup.api.config.ApiPaths;
import com.powerup.api.openapi.UserOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
@RequiredArgsConstructor
public class UserRouterRest {

    private final ApiPaths apiPaths;

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
        return route()
                .POST(apiPaths.getUsers(), handler::listenSaveUser, UserOpenApi::saveUser)
                .GET(apiPaths.getUserByIdentityDocument(), handler::listenFindUserByIdentityDocument, UserOpenApi::findUserByIdentityDocument)
                .build();
    }
}






