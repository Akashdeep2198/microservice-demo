package com.demo.ribbon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRecommendationDTO {
    private String productId;
    private String description;  // Description of the product, fetched from ProductService
    private Double price;        // Price of the product, fetched from ProductService
    private Double averageRating; // Average rating of the product, calculated from ReviewService
}
