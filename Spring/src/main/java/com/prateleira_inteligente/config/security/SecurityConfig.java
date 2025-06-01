package com.prateleira_inteligente.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/livro/create").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/livro/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/livro/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/livro/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::withDefaults); // Forma moderna

        return http.build();
    }*/
}
