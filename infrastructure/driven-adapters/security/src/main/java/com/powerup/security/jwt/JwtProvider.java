package com.powerup.security.jwt;

import com.powerup.model.token.Token;
import com.powerup.model.token.gateways.ITokenProviderPort;
import com.powerup.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider implements ITokenProviderPort {

    private final JwtKeyProvider jwtKeyProvider;
    private final long expirationMillis;

    @Override
    public Mono<Token> createToken(String email, String role) {
        return jwtKeyProvider.loadPrivateKey()
                .map(privateKey -> Token.builder()
                        .accessToken(Jwts.builder()
                                .subject(email)
                                .claim(SecurityConstants.CLAIM_ROLE, role)
                                .issuedAt(new Date())
                                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                                .signWith(privateKey)
                                .compact())
                        .build()
        );
    }
}
