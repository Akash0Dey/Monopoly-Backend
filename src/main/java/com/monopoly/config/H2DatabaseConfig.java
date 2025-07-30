package com.monopoly.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConditionalOnProperty(name = "h2.console.enabled", havingValue = "true")
public class H2DatabaseConfig {
    private static final String[] H2_CONSOLE_WHITELIST = {
            "/h2-console/**"
    };

    @Configuration
    public static class DevelopmentSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher(H2_CONSOLE_WHITELIST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

            return http.build();
        }
    }
}
