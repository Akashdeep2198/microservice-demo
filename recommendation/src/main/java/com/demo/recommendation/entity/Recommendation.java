package com.demo.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    private String id;
    private String productId;
    private String recommendedProductId;
    private String reason;
}
