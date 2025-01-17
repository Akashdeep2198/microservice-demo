package com.demo.ribbon.dto;

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
    private Integer rating;
}
