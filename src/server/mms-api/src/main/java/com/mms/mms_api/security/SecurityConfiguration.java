package com.mms.mms_api.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

/**
 * Central Spring Security configuration for stateless JWT-based authentication.
 */
@Configuration
@AllArgsConstructor
@EnableMethodSecurity
@EnableJpaAuditing
public class SecurityConfiguration {
    private JwtAuthenticationFilter jwtAuthFilter;

    private CustomAuthenticationEntryPoint authEntryPoint;

    private CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Configures HTTP security rules, filter chain, and exception handling.
     *
     * @param httpSecurity security builder
     * @return configured security filter chain
     * @throws Exception when configuration fails
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/app-messages/**").permitAll()
                        .requestMatchers("/api/app-constraints/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .build();
    }

    /**
     * Registers CORS rules for frontend communication.
     *
     * @return CORS configuration source
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Exposes the framework authentication manager.
     *
     * @param config authentication configuration
     * @return authentication manager
     * @throws Exception when manager lookup fails
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides password encoding for stored credentials.
     *
     * @return BCrypt password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the auditor aware implementation for JPA auditing.
     *
     * @return auditor aware for tracking entity modifications
     */
    @Bean
    AuditorAware<String> auditorProvider() {
        return new SecurityAuditorAware();
    }
}
