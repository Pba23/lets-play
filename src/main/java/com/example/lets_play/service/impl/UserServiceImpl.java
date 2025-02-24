package com.example.lets_play.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.exception.UserNotFoundException;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.service.UserService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator; // Ajout du validateur

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public UserDTO createUser(User userDTO) {
        // 🔹 Valide manuellement l'objet
        Set<ConstraintViolation<User>> violations = validator.validate(userDTO);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }
            throw new IllegalArgumentException("Erreur de validation : " + sb.toString().trim());
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hashage du mot de passe
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        // 🔹 Valide manuellement l'objet
        User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }
            throw new IllegalArgumentException("Erreur de validation : " + sb.toString().trim());
        }
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public void deleteUser(String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        userRepository.deleteById(id);
    }
}
