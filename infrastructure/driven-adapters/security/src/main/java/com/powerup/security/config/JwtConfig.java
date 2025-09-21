package com.powerup.security.config;

import com.powerup.security.jwt.JwtProvider;
import com.powerup.security.jwt.JwtKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.keys.private}")
    private String privateKey;

    @Value("${security.jwt.keys.public}")
    private String publicKey;

    @Value("${security.jwt.expiration}")
    private long expirationMillis;

    @Bean
    public JwtKeyProvider jwtKeyProvider() {
        return new JwtKeyProvider(privateKey, publicKey);
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder(JwtKeyProvider jwtKeyProvider) {
        return jwtKeyProvider.loadPublicKey()
                .map(publicKey -> NimbusReactiveJwtDecoder.withPublicKey(publicKey).build())
                .block();
    }

    @Bean
    public JwtProvider jwtProvider(JwtKeyProvider jwtKeyProvider) {
        return new JwtProvider(jwtKeyProvider, expirationMillis);
    }
}
