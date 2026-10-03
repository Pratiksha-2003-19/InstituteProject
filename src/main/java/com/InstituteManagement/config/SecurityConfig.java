package com.InstituteManagement.config;

import com.InstituteManagement.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(auth -> auth

                        // ---------- PUBLIC ----------
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // ---------- ADMIN ONLY ----------
                        .requestMatchers("/api/admin/users/**", "/api/admin/trainers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/batches/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/students/**").hasRole("ADMIN")
                        .requestMatchers("/api/settings/**").hasRole("ADMIN")

                        // ---------- ADMIN + TRAINER ----------
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "TRAINER")
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN", "TRAINER")
                        .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "TRAINER", "STUDENT")
                        .requestMatchers("/api/exams/**").hasAnyRole("ADMIN", "TRAINER", "STUDENT")

                        // ---------- ADMIN + STUDENT ----------
                        .requestMatchers("/api/enrollments/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/payments/**").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers("/api/certificates/**").hasAnyRole("ADMIN", "STUDENT")

                        // ---------- ALL LOGGED-IN USERS ----------
                        .requestMatchers(HttpMethod.GET, "/api/batches/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://institute.com",
                "https://institute.com",
                "http://institute.local",
                "http://institute_fe.com",
                "https://institute_fe.com",
                "http://127.0.0.1",
                "http://localhost:5173",
                "http://localhost:3000",
                "http://localhost:5175"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}