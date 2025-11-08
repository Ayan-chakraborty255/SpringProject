package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // ✅ Enable CORS

                .authorizeHttpRequests(auth -> auth

                        // ✅ PUBLIC APIs (Signup, Login, Verify OTP)
                        .requestMatchers(
                                "/api/user/signup",
                                "/api/user/login",
                                "/api/user/verify"
                        ).permitAll()

                        // ✅ Public pages from Landing frontend
                        .requestMatchers(
                                "/login", "/signup", "/about", "/products", "/pricing", "/support"
                        ).permitAll()

                        // ✅ Protected routes (Dashboard)
                        .requestMatchers(
                                "/api/user/profile",
                                "/api/secure/**",
                                "/dashboard/**"
                        ).authenticated()

                        .anyRequest().permitAll()
                )

                // ✅ Add JWT auth filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
