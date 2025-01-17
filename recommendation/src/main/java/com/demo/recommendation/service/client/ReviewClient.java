package com.demo.recommendation.service.client;

import com.demo.recommendation.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "review-service", url = "http://localhost:8090")
public interface ReviewClient {

    @GetMapping("/api/reviews/product/{productId}")
    List<ReviewDTO> getReviewsByProductId();

    @GetMapping("/api/reviews/product/{productId}/average")
    Double getAverageRating(@PathVariable String productId);

    @GetMapping("/api/reviews/product-ids")
    List<String> getAllProductIds();
}
