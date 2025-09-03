package com.powerup.security.encoder;

import com.powerup.model.user.gateways.IPasswordEncoderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderAdapterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordEncoderAdapter encoderAdapter;

    private String rawPassword;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        rawPassword = "12345";
        encodedPassword = "encoded12345";
    }

    @Test
    @DisplayName("Should encode password correctly")
    void testEncodePassword() {
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = encoderAdapter.encode(rawPassword);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    @DisplayName("Should return true when password matches")
    void testMatchesPasswordSuccess() {
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean matches = encoderAdapter.matches(rawPassword, encodedPassword);

        assertTrue(matches);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Should return false when password does not match")
    void testMatchesPasswordFail() {
        when(passwordEncoder.matches("wrongPassword", encodedPassword)).thenReturn(false);

        boolean matches = encoderAdapter.matches("wrongPassword", encodedPassword);

        assertFalse(matches);
        verify(passwordEncoder, times(1)).matches("wrongPassword", encodedPassword);
    }
}
