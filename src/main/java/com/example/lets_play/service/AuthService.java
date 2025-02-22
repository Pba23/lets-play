package com.example.lets_play.service;

import java.util.Map;

public interface AuthService {
    
    Map<String, String> login(String email, String password);
    boolean validateToken(String token); // 🔥 Vérifie bien cette signature !
}

