package com.demo.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    private String id;
    private String productId;
    private String userId;
    private String comment;
    private Integer rating; // Rating out of 5
}
