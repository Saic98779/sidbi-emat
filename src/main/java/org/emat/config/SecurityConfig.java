package org.emat.config;

import org.emat.repository.UserRepository;
import org.emat.service.EndpointRolePolicyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * Security configuration for the EMAT application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final EndpointRolePolicyService endpointRolePolicyService;

    public SecurityConfig(UserRepository userRepository, EndpointRolePolicyService endpointRolePolicyService) {
        this.userRepository = userRepository;
        this.endpointRolePolicyService = endpointRolePolicyService;
    }

    /**
     * Password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .disabled(!user.isActive())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    /**
     * Security filter chain configuration.
     * Public endpoints: /users/login, /health, Swagger UI
     * All other endpoints require authentication via JWT
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authenticationProvider,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/users/login", "/health", "/", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/users").hasAnyRole(resolveRolesOrDefaults("usersRead", List.of("SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "SIDBI_RO")))
                    .requestMatchers(HttpMethod.GET, "/users/search").hasAnyRole(resolveRolesOrDefaults("usersRead", List.of("SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "SIDBI_RO")))
                    .requestMatchers("/industry-association-registrations/**").hasAnyRole(resolveRolesOrDefaults("industryAssociationRead", List.of("GT_FIELD_TEAM", "GT_PMU", "BSE", "MANPOWER_AGENCY", "SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "CLUSTER_EXPERT")))
                    .requestMatchers("/industry-association-appraisals/**").hasAnyRole(resolveRolesOrDefaults("industryAssociationRead", List.of("GT_FIELD_TEAM", "GT_PMU", "BSE", "MANPOWER_AGENCY", "SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "CLUSTER_EXPERT")))
                    .requestMatchers("/bse-recommendations/**").hasAnyRole(resolveRolesOrDefaults("bseRecommendationRead", List.of("BSE", "GT_FIELD_TEAM", "GT_PMU", "MANPOWER_AGENCY", "SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER", "CLUSTER_EXPERT")))
                    .requestMatchers("/sidbi-sde/**").hasAnyRole(resolveRolesOrDefaults(EndpointRolePolicyService.SIDBI_SDE, List.of("SIDBI_SDE", "SIDBI_RO", "SIDBI_HO_MAKER", "SIDBI_HO_CHECKER")))
                    .requestMatchers("/vendor-disbursements/**").hasAnyRole(resolveRolesOrDefaults("bseRecommendationWrite", List.of("BSE", "GT_FIELD_TEAM", "GT_PMU", "MANPOWER_AGENCY", "SIDBI_HO_MAKER", "SIDBI_RO")))
                    .anyRequest().authenticated()
            );

        return http.build();
    }

    private String[] resolveRolesOrDefaults(String policyKey, List<String> defaults) {
        try {
            return endpointRolePolicyService.resolveRoles(policyKey);
        } catch (IllegalArgumentException ex) {
            return defaults.toArray(String[]::new);
        }
    }
}
