package com.example.lets_play.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody Map<String, String> request) {
        String username = request.get("name");
        String password = request.get("password");
        return authService.login(username, password);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            System.err.println("Utilisateur déjà existant");
            return ResponseEntity.badRequest().body(null);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash du mot de passe
        userRepository.save(user);

        return ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }
}