package com.example.lets_play;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lets_play.dto.ProductDTO;
import com.example.lets_play.dto.UserDTO;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.MockProductRepository;
import com.example.lets_play.repository.MockUserRepository;
import com.example.lets_play.service.impl.ProductServiceImpl;
import com.example.lets_play.service.impl.UserServiceImpl;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProductServiceTest {
    public static void main(String[] args) {
        
        // Mock du repository
        MockProductRepository productRepository = new MockProductRepository();

        // Initialisation du Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        MockUserRepository userRepository = new MockUserRepository();


        // Instanciation du service avec le mock
        ProductServiceImpl productService = new ProductServiceImpl(productRepository,userRepository, validator);
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

        // Instanciation du service avec le mock
        UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder, validator);

        
        User newUser = new User(null, "a", "alice@example.com", "user", "password123");
        UserDTO createdUser = userService.createUser(newUser);
        // 🔹 Test 1: createProduct
        ProductDTO newProduct = new ProductDTO( null,"PS5", "Console de jeu", "499.99", createdUser.getId());
        ProductDTO createdProduct = productService.createProduct(newProduct);
        System.out.println("✅ createProduct : " + createdProduct);

        // 🔹 Test 2: getProductById
        ProductDTO fetchedProduct = productService.getProductById(createdProduct.getId());
        System.out.println("✅ getProductById : " + fetchedProduct);

        // 🔹 Test 3: getAllProducts
        System.out.println("✅ getAllProducts : " + productService.getAllProducts());

        // 🔹 Test 4: updateProduct
        ProductDTO updatedProduct = new ProductDTO(createdProduct.getId(), "PS5 Pro", "Nouvelle version", "599.99", "user123");
        ProductDTO resultUpdate = productService.updateProduct(createdProduct.getId(), updatedProduct);
        System.out.println("✅ updateProduct : " + resultUpdate);

        // 🔹 Test 5: deleteProduct
        productService.deleteProduct(createdProduct.getId());
        System.out.println("✅ deleteProduct : " + productService.getAllProducts());

        // 🔹 Test 6: createProduct avec un prix négatif (doit échouer)
        try {
            ProductDTO invalidProduct = new ProductDTO(null, "Test", "Description", "-10", "user123");
            productService.createProduct(invalidProduct);
        } catch (Exception e) {
            System.out.println("✅ createProduct (prix négatif) : " + e.getMessage());
        }
    }
}
