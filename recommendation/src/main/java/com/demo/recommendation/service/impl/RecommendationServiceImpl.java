package com.demo.recommendation.service.impl;

import com.demo.recommendation.dto.ProductDTO;
import com.demo.recommendation.dto.ProductRecommendationDTO;
import com.demo.recommendation.dto.ReviewDTO;
import com.demo.recommendation.entity.Recommendation;
import com.demo.recommendation.repository.RecommendationRepository;
import com.demo.recommendation.service.client.ProductClient;
import com.demo.recommendation.service.client.RecommendationService;
import com.demo.recommendation.service.client.ReviewClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final ReviewClient reviewClient;
    private final ProductClient productClient;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository, ReviewClient reviewClient, ProductClient productClient) {
        this.recommendationRepository = recommendationRepository;
        this.reviewClient = reviewClient;
        this.productClient = productClient;
    }

    @Override
    public Recommendation saveOrUpdateRecommendation(Recommendation recommendation) {
        log.info("Saving or updating recommendation for product ID: {}", recommendation.getProductId());
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        log.info("Recommendation saved or updated for product ID: {}", recommendation.getProductId());
        return savedRecommendation;
    }

    @Override
    public List<Recommendation> getRecommendationsByProductId(String productId) {
        log.info("Fetching recommendations for product ID: {}", productId);
        List<Recommendation> recommendations = recommendationRepository.findByProductId(productId);
        log.info("Fetched {} recommendations for product ID: {}", recommendations.size(), productId);
        return recommendations;
    }

    @Override
    public void deleteRecommendationById(String id) {
        log.info("Attempting to delete recommendation with ID: {}", id);
        if (recommendationRepository.existsById(id)) {
            recommendationRepository.deleteById(id);
            log.info("Successfully deleted recommendation with ID: {}", id);
        } else {
            log.error("Recommendation not found with ID: {}", id);
            throw new RuntimeException("Recommendation not found with id: " + id);
        }
    }

    @Override
    public List<ProductRecommendationDTO> getTopRatedProducts() {
        log.info("Fetching all product IDs for top-rated products");
        List<String> productIds = reviewClient.getAllProductIds(); // Assuming this returns all product IDs
        List<ProductRecommendationDTO> recommendations = new ArrayList<>();

        for (String productId : productIds) {
            log.info("Fetching average rating for product ID: {}", productId);
            Double averageRating = reviewClient.getAverageRating(productId);

            log.info("Fetching product details for product ID: {}", productId);
            ProductDTO product = productClient.getProductById(productId);

            recommendations.add(new ProductRecommendationDTO(
                    productId,
                    product.getDesc(),
                    product.getPrice(),
                    averageRating
            ));
        }

        recommendations.sort((p1, p2) -> Double.compare(p2.getAverageRating(), p1.getAverageRating()));
        log.info("Sorted top-rated products by average rating");

        return recommendations;
    }
}
