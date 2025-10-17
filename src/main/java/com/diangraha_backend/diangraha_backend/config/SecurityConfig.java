package com.diangraha_backend.diangraha_backend.config;

import com.diangraha_backend.diangraha_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth
                        /* ==== AUTH ==== */
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        /* ==== CONTACT-MESSAGES ==== */
                        .requestMatchers(HttpMethod.POST, "/api/contact-messages").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/contact-messages/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/sub-services/**").permitAll()

                        /* ==== CLIENTS ==== */
                        .requestMatchers(HttpMethod.GET, "/api/clients/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clients/**").authenticated()

                        /* ==== BRANDS ==== */
                        .requestMatchers(HttpMethod.GET, "/api/brands/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/brands/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/brands/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/brands/**").authenticated()

                        /* ==== SERVICES ==== */
                        .requestMatchers(HttpMethod.GET, "/api/services/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/services/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/services/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/services/**").authenticated()

                        /* ==== ACHIEVEMENTS ==== */
                        .requestMatchers(HttpMethod.GET, "/api/achievements/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/achievements/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/achievements/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/achievements/**").authenticated()

                        /* ==== SWAGGER & FILES ==== */
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/uploads/**").permitAll()

                        /* ==== DEFAULT ==== */
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("*"));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
