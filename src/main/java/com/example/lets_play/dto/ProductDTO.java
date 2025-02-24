package com.example.lets_play.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    @NotBlank(message = "Le nom du produit est obligatoire.")
    private String name;

    @NotBlank(message = "La description est obligatoire.")
    private String description;

    @Positive(message = "Le prix doit être un nombre positif.")
    private String price;
    private String userId;
}
