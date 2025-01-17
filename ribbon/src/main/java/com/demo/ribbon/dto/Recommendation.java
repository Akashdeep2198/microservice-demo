package com.demo.ribbon.dto;

import lombok.Data;


@Data
public class Recommendation {
    private String id;
    private String productId;
    private String recommendedProductId;
    private String reason;
}
