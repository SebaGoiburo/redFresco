package com.fresco.fresco.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Autowired
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
