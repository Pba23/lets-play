package com.example.lets_play.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.lets_play.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        return authService.login(email,password);
    }
}