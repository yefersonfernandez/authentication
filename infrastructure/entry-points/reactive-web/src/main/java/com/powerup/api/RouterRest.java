package com.powerup.api;

import com.powerup.api.config.UserPath;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UserPath userPath;

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/users", produces = {MediaType.APPLICATION_JSON_VALUE,}, method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "listenSaveUser"),
            @RouterOperation(path = "/api/v1/users/userByIdentityDocument/{identityDocument}", produces = {MediaType.APPLICATION_JSON_VALUE,}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "listenFindUserByIdentityDocument"),
    })
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route(POST(userPath.getUsers()), handler::listenSaveUser)
                .andRoute(GET(userPath.getUserByIdentityDocument()),  handler::listenFindUserByIdentityDocument);
    }
}






