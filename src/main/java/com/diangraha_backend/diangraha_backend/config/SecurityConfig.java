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
                        /* ==== AUTH ENDPOINTS ==== */
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                        /* ==== CONTACT-MESSAGES ==== */
                        .requestMatchers(HttpMethod.POST, "/api/contact-messages").permitAll()   // form publik
                        .requestMatchers(HttpMethod.GET, "/api/contact-messages/**").authenticated() // wajib login
                        .requestMatchers(HttpMethod.POST,"/api/achievements/**", "/api/brands/**", "/api/services/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/achievements/**", "/api/brands/**", "/api/services/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/achievements/**", "/api/brands/**", "/api/services/**").authenticated()
                        /* ==== SWAGGER ==== */
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html",
                                "/v3/api-docs/**", "/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                        /* ==== PUBLIC ENDPOINTS ==== */
                        .requestMatchers(HttpMethod.GET,"/api/achievements/**", "/api/brands/**", "/api/services/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

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
