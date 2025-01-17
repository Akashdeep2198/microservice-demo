package com.demo.review.config;

import com.demo.review.dto.ProductsContactInfoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductsConfig {

    @Bean
    public ProductsContactInfoDto productsContactInfoDto() {
        return new ProductsContactInfoDto();
    }
}
