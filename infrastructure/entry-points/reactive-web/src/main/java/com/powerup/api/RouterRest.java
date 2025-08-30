package com.powerup.api;

import com.powerup.api.config.UserPath;
import com.powerup.api.openapi.UserOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UserPath userPath;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route()
                .POST(userPath.getUsers(), handler::listenSaveUser, UserOpenApi::saveUser)
                .GET(userPath.getUserByIdentityDocument(), handler::listenFindUserByIdentityDocument, UserOpenApi::findUserByIdentityDocument)
                .build();
    }
}






