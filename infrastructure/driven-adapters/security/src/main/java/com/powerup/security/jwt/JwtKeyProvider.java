package com.powerup.security.jwt;

import com.powerup.security.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtKeyProvider {

    private final String privateKey;
    private final String publicKey;

    public Mono<PrivateKey> loadPrivateKey() {
        return Mono.fromCallable(() -> {
            byte[] keyBytes = readKeyBytes(privateKey,
                    SecurityConstants.PRIVATE_KEY_HEADER,
                    SecurityConstants.PRIVATE_KEY_FOOTER);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance(SecurityConstants.ALGORITHM_RSA).generatePrivate(spec);
        });
    }

    public Mono<RSAPublicKey> loadPublicKey() {
        return Mono.fromCallable(() -> {
            byte[] keyBytes = readKeyBytes(publicKey,
                    SecurityConstants.PUBLIC_KEY_HEADER,
                    SecurityConstants.PUBLIC_KEY_FOOTER);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return (RSAPublicKey) KeyFactory.getInstance(SecurityConstants.ALGORITHM_RSA).generatePublic(spec);
        });
    }

    private byte[] readKeyBytes(String key, String header, String footer) {
        String keyString = key
                .replace(header, "")
                .replace(footer, "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(keyString);
    }
}
