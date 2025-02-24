package com.example.lets_play.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Le nom du produit est obligatoire.")
    private String name;

    @NotBlank(message = "La description est obligatoire.")
    private String description;

    @Positive(message = "Le prix doit être un nombre positif.")
    private String price;

    @NotBlank(message = "L'ID de l'utilisateur est obligatoire.")
    private String userId;
}
