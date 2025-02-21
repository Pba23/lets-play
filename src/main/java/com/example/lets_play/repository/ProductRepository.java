package com.example.lets_play.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.lets_play.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {}
