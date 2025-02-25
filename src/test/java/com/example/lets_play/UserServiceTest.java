package com.example.lets_play;

import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.MockUserRepository;
import com.example.lets_play.service.impl.UserServiceImpl;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserServiceTest {
    public static void main(String[] args) {
        // Mock du repository et encoder
        MockUserRepository userRepository = new MockUserRepository();
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return "hashed_" + rawPassword.toString(); // Simule un hash
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals("hashed_" + rawPassword.toString());
            }
        };
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Instanciation du service avec le mock
        UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder, validator);

        // 🔹 Test 1: createUser
        User newUser = new User(null, "a", "alice@example.com", "user", "password123");
        UserDTO createdUser = userService.createUser(newUser);
        System.out.println("✅ createUser : " + createdUser);

        // 🔹 Test 2: getUserById
        UserDTO fetchedUser = userService.getUserById(createdUser.getId());
        System.out.println("✅ getUserById : " + fetchedUser);

        // 🔹 Test 3: getAllUsers
        System.out.println("✅ getAllUsers : " + userService.getAllUsers());

        // 🔹 Test 4: updateUser
        UserDTO updatedUser = new UserDTO(createdUser.getId(), "AliceUpdated", "alice.updated@example.com", "admin");
        ResponseEntity<UserDTO> resultUpdate = userService.updateUser(createdUser.getId(), updatedUser);
        System.out.println("✅ updateUser : " + resultUpdate);

        // 🔹 Test 5: deleteUser
        userService.deleteUser(createdUser.getId());
        System.out.println("✅ deleteUser : " + userService.getAllUsers());

        // 🔹 Test 6: getUserById (not found)
        try {
            userService.getUserById("invalid_id");
        } catch (Exception e) {
            System.out.println("✅ getUserById (not found) : " + e.getMessage());
        }
    }
}
