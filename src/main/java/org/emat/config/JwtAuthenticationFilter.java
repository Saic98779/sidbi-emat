package org.emat.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * JWT Authentication Filter
 * Intercepts requests and validates JWT tokens from Authorization header
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecretKey signingKey;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(@Value("${jwt.secret}") String jwtSecret,
                                   UserDetailsService userDetailsService) {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        logger.debug("Processing request to: " + request.getRequestURI());
        logger.debug("Authorization header: " + (authHeader != null ? "Present" : "Not present"));

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.debug("JWT token found, attempting to validate");

            try {
                Claims claims = Jwts.parser()
                        .verifyWith(signingKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String username = claims.getSubject();
                logger.debug("JWT token validated successfully for user: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Create authentication token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("SecurityContext set for user: " + username);
                }
            } catch (Exception e) {
                // Invalid token - continue without authentication
                logger.error("JWT validation failed: " + e.getMessage(), e);
            }
        }

        filterChain.doFilter(request, response);
    }
}

