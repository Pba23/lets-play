package com.example.lets_play;


import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.MockUserRepository;
import com.example.lets_play.service.impl.AuthServiceImpl;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceTest {
    public static void main(String[] args) {
        MockUserRepository userRepository = new MockUserRepository();

        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return "hashed_" + rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals("hashed_" + rawPassword.toString());
            }
        };

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        AuthServiceImpl authService = new AuthServiceImpl(userRepository, passwordEncoder, validator);

        // 🔹 Test 1: register (inscription réussie)
        User newUser = new User(null, "Alice", "alice@example.com", "user", "password123");
        try {
            UserDTO createdUser = authService.register(newUser);
            System.out.println("✅ register : " + createdUser);
        } catch (Exception e) {
            System.out.println("❌ register : " + e.getMessage());
        }

        // 🔹 Test 2: register (nom vide)
        try {
            User invalidUser = new User(null, "", "invalid@example.com", "user", "password123");
            authService.register(invalidUser);
        } catch (Exception e) {
            System.out.println("✅ register (nom vide) : " + e.getMessage());
        }

        // 🔹 Test 3: register (email invalide)
        try {
            User invalidUser = new User(null, "Bob", "invalid-email", "user", "password123");
            authService.register(invalidUser);
        } catch (Exception e) {
            System.out.println("✅ register (email invalide) : " + e.getMessage());
        }

        // 🔹 Test 4: register (rôle invalide)
        try {
            User invalidUser = new User(null, "Charlie", "charlie@example.com", "adminn", "password123");
            authService.register(invalidUser);
        } catch (Exception e) {
            System.out.println("✅ register (rôle invalide) : " + e.getMessage());
        }

        // 🔹 Test 5: login avec succès
        try {
            var tokenResponse = authService.login("Alice", "password123");
            System.out.println("✅ login : " + tokenResponse);
        } catch (Exception e) {
            System.out.println("❌ login (should pass) : " + e.getMessage());
        }

        // 🔹 Test 6: login avec mot de passe incorrect
        try {
            authService.login("Alice", "wrongpassword");
        } catch (Exception e) {
            System.out.println("✅ login (mauvais mot de passe) : " + e.getMessage());
        }

        // 🔹 Test 7: login avec username inexistant
        try {
            authService.login("Bob", "password123");
        } catch (Exception e) {
            System.out.println("✅ login (utilisateur inexistant) : " + e.getMessage());
        }

        // 🔹 Test 8: login avec username vide
        try {
            authService.login("", "password123");
        } catch (Exception e) {
            System.out.println("✅ login (username vide) : " + e.getMessage());
        }

        // 🔹 Test 9: login avec password vide
        try {
            authService.login("Alice", "");
        } catch (Exception e) {
            System.out.println("✅ login (password vide) : " + e.getMessage());
        }
    }
}
