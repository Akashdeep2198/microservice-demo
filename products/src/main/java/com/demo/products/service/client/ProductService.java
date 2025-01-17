package com.demo.products.service.client;

import com.demo.products.dto.ProductDTO;
import com.demo.products.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveOrUpdateProduct(Product product);

    Product getProductById(String id);

    List<Product> getAllProducts();

    void deleteProductById(String id);

    ProductDTO getProductWithReviews(String productId);
}
