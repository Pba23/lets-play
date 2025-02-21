package com.example.lets_play.service;

import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.model.User;

import java.util.List;


public interface UserService {
    UserDTO createUser(User userDTO);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);
}
