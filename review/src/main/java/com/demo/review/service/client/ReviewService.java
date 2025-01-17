package com.demo.review.service.client;


import com.demo.review.dto.ReviewDTO;
import com.demo.review.entity.Review;

import java.util.List;
import java.util.Set;

public interface ReviewService {
    ReviewDTO saveOrUpdateReview(ReviewDTO review);

    List<ReviewDTO> getReviewsByProductId(String productId);

    void deleteReviewById(String id);

    Set<String> getAllProductIds();

    Double getAverageRatingByProductId(String productId);
}
