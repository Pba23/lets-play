package com.example.lets_play.service;

import java.util.List;
import com.example.lets_play.dto.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(String id, ProductDTO productDTO);
    void deleteProduct(String id);
}
