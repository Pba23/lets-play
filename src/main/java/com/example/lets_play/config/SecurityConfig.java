package com.example.lets_play.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.security.CustomAccessDeniedHandler;
import com.example.lets_play.security.CustomAuthenticationEntryPoint;
import com.example.lets_play.security.JwtAuthenticationFilter;
import com.example.lets_play.service.CustomUserDetailsService;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler,
            CustomUserDetailsService userDetailsService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN") // Utiliser "ADMIN" au
                                                                                                 // lieu de "admin"
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN") // Utiliser "ADMIN" au
                                                                                              // lieu de "admin"
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint) // Capture 401
                        .accessDeniedHandler(accessDeniedHandler)) // Capture 403
                .addFilterBefore(jwtAuthenticationFilter(userDetailsService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(CustomUserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        corsConfig.setAllowedHeaders(
                Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Authorization"));
        corsConfig.setExposedHeaders(Arrays.asList("Authorization"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.example.lets_play.model.User user = userRepository.findByName(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
                return new org.springframework.security.core.userdetails.User(
                        user.getId().toString(), // Utiliser l'ID comme nom d'utilisateur
                        user.getPassword(),
                        user.getAuthorities() // Récupère les autorités (rôles)
                );
            }

            @Override
            public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
                com.example.lets_play.model.User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
                return new org.springframework.security.core.userdetails.User(
                        user.getId().toString(), // Utiliser l'ID comme nom d'utilisateur
                        user.getPassword(),
                        user.getAuthorities() // Récupère les autorités (rôles)
                );
            }
        };
    }
}