package com.demo.review.controller;

import com.demo.review.dto.ProductsContactInfoDto;
import com.demo.review.dto.ReviewDTO;
import com.demo.review.service.client.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final String buildVersion;
    private final ProductsContactInfoDto productsContactInfoDto;

    @Autowired
    public ReviewController(ReviewService reviewService, @Value("${build.version}") String buildVersion, ProductsContactInfoDto productsContactInfoDto) {
        this.reviewService = reviewService;
        this.buildVersion = buildVersion;
        this.productsContactInfoDto = productsContactInfoDto;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createOrUpdateReview(@RequestBody ReviewDTO review) {
        log.info("Request received to create or update review for product ID: {}", review.getProductId());
        ReviewDTO savedReview = reviewService.saveOrUpdateReview(review);
        log.info("Review saved or updated for product ID: {}", review.getProductId());
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable String productId) {
        log.info("Fetching reviews for product ID: {}", productId);
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        log.info("Fetched {} reviews for product ID: {}", reviews.size(), productId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable String id) {
        log.info("Attempting to delete review with ID: {}", id);
        reviewService.deleteReviewById(id);
        log.info("Successfully deleted review with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product-ids")
    public ResponseEntity<Set<String>> getAllProductIds() {
        log.info("Fetching all product IDs with reviews");
        Set<String> productIds = reviewService.getAllProductIds();
        log.info("Fetched {} product IDs with reviews", productIds.size());
        return ResponseEntity.ok(productIds);
    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable String productId) {
        log.info("Calculating average rating for product ID: {}", productId);
        Double averageRating = reviewService.getAverageRatingByProductId(productId);
        log.info("Calculated average rating for product ID {}: {}", productId, averageRating);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<ProductsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productsContactInfoDto);
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }
}
