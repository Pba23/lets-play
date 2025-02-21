package com.example.lets_play.service.impl;

import org.springframework.stereotype.Service;
import com.example.lets_play.dto.ProductDTO;
import com.example.lets_play.model.Product;
import com.example.lets_play.model.User;
import com.example.lets_play.repository.ProductRepository;
import com.example.lets_play.repository.UserRepository;
import com.example.lets_play.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        User user = userRepository.findById(productDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setUserId(user.getId());
        productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), user.getId());
    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getUserId());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getUserId()))
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getUserId());
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
