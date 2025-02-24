package com.example.lets_play.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.exception.InvalidCredentialsException;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.service.AuthService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;

import java.security.Key;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    private static final String SECRET_STRING = "MaSuperCleSecretePourJWTQuiEstAssezLongue123!";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_STRING.getBytes()));

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    /**
     * Méthode de validation pour User
     */
    private void validate(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation échouée: " + violations.iterator().next().getMessage());
        }
    }

    /**
     * Enregistre un nouvel utilisateur après validation.
     */
    public UserDTO register(User user) {
        validate(user); // Vérification des contraintes
        user.setPassword(encodePassword(user.getPassword())); // Hashage du mot de passe
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    /**
     * Encode un mot de passe en utilisant PasswordEncoder.
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Authentifie un utilisateur et génère un token JWT.
     */
    public Map<String, String> login(@NotBlank String username, @NotBlank String password) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new InvalidCredentialsException("Nom d'utilisateur invalide"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Identifiants invalides");
        }

        String token = Jwts.builder()
                .setSubject(user.getName())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 jour
                .signWith(SECRET_KEY)
                .compact();

        return Collections.singletonMap("token", token);
    }

    /**
     * Valide un token JWT.
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
