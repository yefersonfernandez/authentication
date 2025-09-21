package com.powerup.security.constants;

public final class SecurityConstants {

    private SecurityConstants() {}

    public static final String PRIVATE_KEY_HEADER = "-----BEGIN PRIVATE KEY-----";
    public static final String PRIVATE_KEY_FOOTER = "-----END PRIVATE KEY-----";
    public static final String PUBLIC_KEY_HEADER = "-----BEGIN PUBLIC KEY-----";
    public static final String PUBLIC_KEY_FOOTER = "-----END PUBLIC KEY-----";
    public static final String ALGORITHM_RSA = "RSA";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_ADVISOR = "ADVISOR";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String CLAIM_ROLE = "role";

    public static final String ACTUATOR_HEALTH_URL = "/auth/actuator/health";
    public static final String LOGIN_URL = "/auth/api/v1/login";
    public static final String USER_CREATION_URL = "/auth/api/v1/users";
    public static final String[] PUBLIC_SWAGGER_PATHS = {
            "/auth/api/doc/**", "/auth/v3/api-docs/**",
            "/auth/swagger-ui.html", "/auth/swagger-ui/**"
    };
}
