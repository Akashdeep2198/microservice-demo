package com.demo.recommendation.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private String id;
    private String productId;
    private String userId;
    private String comment;
    private Integer rating; // Rating out of 5
}
