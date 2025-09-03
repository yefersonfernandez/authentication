package com.powerup.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class ApiPaths {
    private String users;
    private String userByIdentityDocument;
    private String login;
}
