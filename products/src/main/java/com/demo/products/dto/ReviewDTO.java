package com.demo.products.dto;

import lombok.Data;


@Data
public class ReviewDTO {
    private String id;
    private String productId;
    private String userId;
    private String comment;
    private Integer rating; // Rating out of 5
}
