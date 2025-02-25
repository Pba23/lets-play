package com.example.lets_play.service;

import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.model.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDTO createUser(User userDTO);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    ResponseEntity<UserDTO> updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);
}
