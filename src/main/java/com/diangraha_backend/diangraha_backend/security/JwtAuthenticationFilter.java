package com.diangraha_backend.diangraha_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Endpoint yang benar-benar publik (tidak perlu token sama sekali)
    private static final List<String> ALWAYS_PUBLIC_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/swagger-ui",
            "/v3/api-docs",
            "/uploads"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean isAlwaysPublic = ALWAYS_PUBLIC_PATHS.stream().anyMatch(path::startsWith);
        if (isAlwaysPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        if ("GET".equalsIgnoreCase(method) &&
                (path.startsWith("/api/brands") ||
                        path.startsWith("/api/services") ||
                        path.startsWith("/api/achievements") ||
                        path.startsWith("/api/clients") ||
                        path.startsWith("/api/sub-services")
                )) {
            filterChain.doFilter(request, response);
            return;
        }

        if ("POST".equalsIgnoreCase(method) && path.startsWith("/api/contact-messages")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        try {
            final String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // token invalid → lanjut aja tanpa authentication
        }

        filterChain.doFilter(request, response);
    }
}
