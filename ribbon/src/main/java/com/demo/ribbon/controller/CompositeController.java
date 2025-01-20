package com.demo.ribbon.controller;

import com.demo.ribbon.client.ProductClient;
import com.demo.ribbon.client.ReviewClient;
import com.demo.ribbon.dto.ProductDTO;
import com.demo.ribbon.dto.ProductWithReviewsDTO;
import com.demo.ribbon.dto.ReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/composite")
public class CompositeController {

    @LoadBalanced
    private final ProductClient productClient;
    private final ReviewClient reviewClient;
    private final LoadBalancerClient loadBalancerClient;

    public CompositeController(ProductClient productClient, ReviewClient reviewClient, LoadBalancerClient loadBalancerClient) {
        this.productClient = productClient;
        this.reviewClient = reviewClient;
        this.loadBalancerClient = loadBalancerClient;
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

    @GetMapping("/GetEndpoint")
    public String greeting() {
        log.info("Invoking /GetEndpoint to retrieve an instance of the 'products' service.");

        ServiceInstance instance = loadBalancerClient.choose("products");
        if (instance == null) {
            log.error("No instances available for service: 'products'.");
            return "Service unavailable";
        }

        String host = instance.getHost();
        int port = instance.getPort();
        log.info("Found an instance of 'products' service - Host: {}, Port: {}", host, port);

        URI storesUri = URI.create(String.format("http://%s:%s", host, port));
        log.info("Constructed service URI: {}", storesUri);

        return storesUri.toString();
    }
}
