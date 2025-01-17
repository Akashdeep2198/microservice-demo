package com.demo.products.service.impl;

import com.demo.products.dto.ProductDTO;
import com.demo.products.dto.ReviewDTO;
import com.demo.products.entity.Product;
import com.demo.products.repository.ProductRepository;
import com.demo.products.service.client.ProductService;
import com.demo.products.service.client.ReviewFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewFeignClient reviewFeignClient;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ReviewFeignClient reviewFeignClient) {
        this.productRepository = productRepository;
        this.reviewFeignClient = reviewFeignClient;
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        log.info("Saving or updating product with ID: {}", product.getId());
        Product savedProduct = productRepository.save(product);
        log.info("Product saved or updated successfully with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product getProductById(String id) {
        log.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", id);
                    return new RuntimeException("Product not found with id: " + id);
                });
        log.info("Product fetched successfully with ID: {}", id);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        log.info("Fetched {} products", products.size());
        return products;
    }

    @Override
    public void deleteProductById(String id) {
        log.info("Attempting to delete product with ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            log.info("Product deleted successfully with ID: {}", id);
        } else {
            log.error("Product not found with ID: {}", id);
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public ProductDTO getProductWithReviews(String productId) {
        log.info("Fetching product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new RuntimeException("Product not found");
                });

        log.info("Fetching reviews for product with ID: {}", productId);
        List<ReviewDTO> reviews = reviewFeignClient.getReviewsByProductId(productId);

        log.info("Fetched {} reviews for product with ID: {}", reviews.size(), productId);

        return new ProductDTO(
                product.getId(),
                product.getDesc(),
                product.getPrice(),
                reviews
        );
    }
}
