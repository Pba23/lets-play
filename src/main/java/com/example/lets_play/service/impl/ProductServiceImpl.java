package com.example.lets_play.service.impl;

import org.springframework.stereotype.Service;

import com.example.lets_play.dto.ProductDTO;
import com.example.lets_play.exception.UnprocessableEntityException;
import com.example.lets_play.exception.UserNotFoundException;
import com.example.lets_play.model.Product;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.ProductRepository;
import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.service.ProductService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final Validator validator;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, Validator validator) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    /**
     * Méthode de validation générique pour Product
     */
    private void validate(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation échouée: " + violations.iterator().next().getMessage());
        }
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        User user = userRepository.findById(productDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
        double price;
        try {
            price = Double.parseDouble(productDTO.getPrice());
        } catch (NumberFormatException e) {
            throw new UnprocessableEntityException("Le prix doit être un nombre valide.");
        }

        // Validation : Le prix doit être positif
        if (price < 0) {
            throw new UnprocessableEntityException("Le prix doit être positif.");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setUserId(user.getId());

        validate(product); // 🔹 Vérification des contraintes avant sauvegarde

        productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                user.getId());
    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Produit non trouvé"));
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getUserId());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(),
                        product.getDescription(),
                        product.getPrice(), product.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Produit non trouvé"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        validate(product); // 🔹 Vérification avant mise à jour

        productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getUserId());
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Produit non trouvé"));
        productRepository.deleteById(id);
    }
}
