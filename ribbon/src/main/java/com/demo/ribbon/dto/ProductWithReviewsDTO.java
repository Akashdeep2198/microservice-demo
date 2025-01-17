package com.demo.ribbon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithReviewsDTO {
    private ProductDTO product;
    private List<ReviewDTO> reviews;
    private Double averageRating;
}