package com.rahul.cartify.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final CustomUserDetailsService
            customUserDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthFilter,
            CustomUserDetailsService customUserDetailsService
    ) {

        this.jwtAuthFilter = jwtAuthFilter;
        this.customUserDetailsService =
                customUserDetailsService;
    }

    // password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // authentication provider
@Bean
public AuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(
                    customUserDetailsService
            );

    provider.setPasswordEncoder(
            passwordEncoder()
    );

    return provider;
}

    // security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // disable csrf
                .csrf(csrf -> csrf.disable())

                // authorize routes
                .authorizeHttpRequests(auth -> auth

        // public auth routes
        .requestMatchers(
                "/api/auth/**"
        ).permitAll()

        // public GET product routes
        .requestMatchers(
        org.springframework.http.HttpMethod.GET,
        "/api/products",
        "/api/products/**"
).permitAll()


         // cart routes
     // cart routes
.requestMatchers(
        org.springframework.http.HttpMethod.POST,
        "/api/cart/**"
).authenticated()

.requestMatchers(
        org.springframework.http.HttpMethod.GET,
        "/api/cart/**"
).authenticated()

.requestMatchers(
        org.springframework.http.HttpMethod.DELETE,
        "/api/cart/**"
).authenticated()

.requestMatchers(
        org.springframework.http.HttpMethod.PUT,
        "/api/cart/**"
).authenticated()

.requestMatchers(
        org.springframework.http.HttpMethod.POST,
        "/api/categories/**"
).hasRole("ADMIN")

    // public category routes
    .requestMatchers(
            org.springframework.http.HttpMethod.GET,
            "/api/categories",
            "/api/categories/**"
    ).permitAll()

        // ADMIN only routes
        .requestMatchers(
                org.springframework.http.HttpMethod.POST,
                "/api/products/**"
        ).hasRole("ADMIN")

        .requestMatchers(
                org.springframework.http.HttpMethod.PUT,
                "/api/products/**"
        ).hasRole("ADMIN")

        .requestMatchers(
                org.springframework.http.HttpMethod.DELETE,
                "/api/products/**"
        ).hasRole("ADMIN")


      

        // everything else authenticated
        .anyRequest()
        .authenticated()
)

                // stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // add jwt filter
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}