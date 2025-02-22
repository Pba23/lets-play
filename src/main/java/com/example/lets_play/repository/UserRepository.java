package com.example.lets_play.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.lets_play.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}
