package com.example.lets_play.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface AuthService {
    
    ResponseEntity<Map<String, String>> login(String email, String password);
    boolean validateToken(String token); // 🔥 Vérifie bien cette signature !
}

