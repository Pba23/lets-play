package com.example.lets_play.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id; 
    private String name; 
    private String description; 
    private Double price;
    private String userId; 
}
