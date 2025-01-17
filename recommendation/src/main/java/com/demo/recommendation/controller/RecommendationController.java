package com.demo.recommendation.controller;

import com.demo.recommendation.dto.ProductRecommendationDTO;
import com.demo.recommendation.entity.Recommendation;
import com.demo.recommendation.service.client.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<Recommendation> createOrUpdateRecommendation(@RequestBody Recommendation recommendation) {
        log.info("Received request to create or update recommendation for product ID: {}", recommendation.getProductId());
        Recommendation savedRecommendation = recommendationService.saveOrUpdateRecommendation(recommendation);
        log.info("Successfully saved or updated recommendation for product ID: {}", recommendation.getProductId());
        return ResponseEntity.ok(savedRecommendation);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByProductId(@PathVariable String productId) {
        log.info("Fetching recommendations for product ID: {}", productId);
        List<Recommendation> recommendations = recommendationService.getRecommendationsByProductId(productId);
        log.info("Fetched {} recommendations for product ID: {}", recommendations.size(), productId);
        return ResponseEntity.ok(recommendations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendationById(@PathVariable String id) {
        log.info("Received request to delete recommendation with ID: {}", id);
        recommendationService.deleteRecommendationById(id);
        log.info("Successfully deleted recommendation with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<ProductRecommendationDTO>> getTopRatedProducts() {
        log.info("Fetching top-rated products for recommendations");
        List<ProductRecommendationDTO> recommendations = recommendationService.getTopRatedProducts();
        log.info("Fetched {} top-rated products for recommendations", recommendations.size());
        return ResponseEntity.ok(recommendations);
    }
}
