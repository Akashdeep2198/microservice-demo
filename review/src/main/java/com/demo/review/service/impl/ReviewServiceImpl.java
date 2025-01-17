package com.demo.review.service.impl;

import com.demo.review.dto.ReviewDTO;
import com.demo.review.entity.Review;
import com.demo.review.repository.ReviewRepository;
import com.demo.review.service.client.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ObjectMapper objectMapper) {
        this.reviewRepository = reviewRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ReviewDTO saveOrUpdateReview(ReviewDTO reviewDTO) {
        log.info("Request received to save or update review for product ID: {}", reviewDTO.getProductId());
        Review review = objectMapper.convertValue(reviewDTO, Review.class);
        Review savedReview = reviewRepository.save(review);
        log.info("Review saved or updated for product ID: {}", reviewDTO.getProductId());
        return objectMapper.convertValue(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(String productId) {
        log.info("Fetching reviews for product ID: {}", productId);
        List<Review> reviews = reviewRepository.findByProductId(productId);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        reviews.forEach(review -> {
            ReviewDTO reviewDTO = objectMapper.convertValue(review, ReviewDTO.class);
            reviewDTOS.add(reviewDTO);
        });
        log.info("Fetched {} reviews for product ID: {}", reviewDTOS.size(), productId);
        return reviewDTOS;
    }

    @Override
    public void deleteReviewById(String id) {
        log.info("Attempting to delete review with ID: {}", id);
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            log.info("Successfully deleted review with ID: {}", id);
        } else {
            log.error("Review not found with ID: {}", id);
            throw new RuntimeException("Review not found with id: " + id);
        }
    }

    @Override
    public Set<String> getAllProductIds() {
        log.info("Fetching all product IDs with reviews");
        List<Review> reviews = reviewRepository.findAll();
        Set<String> productIds = reviews.stream()
                .map(Review::getProductId)
                .collect(Collectors.toSet());
        log.info("Fetched {} unique product IDs with reviews", productIds.size());
        return productIds;
    }

    @Override
    public Double getAverageRatingByProductId(String productId) {
        log.info("Calculating average rating for product ID: {}", productId);
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            log.error("No reviews found for product ID: {}", productId);
            throw new RuntimeException("No reviews found for productId: " + productId);
        }
        Double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        log.info("Calculated average rating for product ID {}: {}", productId, averageRating);
        return averageRating;
    }
}
