package com.demo.recommendation.service.client;



import com.demo.recommendation.dto.ProductRecommendationDTO;
import com.demo.recommendation.entity.Recommendation;

import java.util.List;

public interface RecommendationService {
    Recommendation saveOrUpdateRecommendation(Recommendation recommendation);

    List<Recommendation> getRecommendationsByProductId(String productId);

    void deleteRecommendationById(String id);

    List<ProductRecommendationDTO> getTopRatedProducts();
}
