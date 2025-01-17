package com.demo.ribbon.controller;

import com.demo.ribbon.client.ProductClient;
import com.demo.ribbon.client.ReviewClient;
import com.demo.ribbon.dto.ProductDTO;
import com.demo.ribbon.dto.ProductWithReviewsDTO;
import com.demo.ribbon.dto.ReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/composite")
public class CompositeController {

    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    public CompositeController(ProductClient productClient, ReviewClient reviewClient) {
        this.productClient = productClient;
        this.reviewClient = reviewClient;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductWithReviewsDTO> getProductWithReviews(@PathVariable String productId) {
        try {
            log.info("Fetching product details for ID: {}", productId);

            ProductDTO product = productClient.getProductById(productId);
            if (product == null) {
                log.warn("Product not found for ID: {}", productId);
                return ResponseEntity.notFound().build();
            }

            List<ReviewDTO> reviews = reviewClient.getReviewsByProductId(productId);
            Double averageRating = reviewClient.getAverageRating(productId);

            ProductWithReviewsDTO response = new ProductWithReviewsDTO(product, reviews, averageRating);
            log.info("Successfully fetched product and reviews for ID: {}", productId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error fetching product or reviews for ID: {}", productId, e);
            return ResponseEntity.status(500).build();
        }
    }
}
