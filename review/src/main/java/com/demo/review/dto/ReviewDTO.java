package com.demo.review.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private String id;
    private String productId;
    private String userId;
    private String comment;
    private Integer rating;
}
