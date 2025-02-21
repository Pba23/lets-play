package com.example.lets_play.service;

public interface AuthService {
    String login(String email, String password);
    boolean validateToken(String token); // 🔥 Vérifie bien cette signature !
}

