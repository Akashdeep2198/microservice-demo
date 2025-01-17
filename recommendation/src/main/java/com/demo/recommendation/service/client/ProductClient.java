package com.demo.recommendation.service.client;

import com.demo.recommendation.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8080")
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    ProductDTO getProductById(@PathVariable String productId);
}
