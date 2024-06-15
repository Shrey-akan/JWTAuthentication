package com.example.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}

