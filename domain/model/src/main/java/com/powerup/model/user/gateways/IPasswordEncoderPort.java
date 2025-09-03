package com.powerup.model.user.gateways;

public interface IPasswordEncoderPort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}