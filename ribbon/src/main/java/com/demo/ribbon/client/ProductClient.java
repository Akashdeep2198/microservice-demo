package com.demo.ribbon.client;

import com.demo.ribbon.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8080")
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    ProductDTO getProductById(@PathVariable String productId);
}
