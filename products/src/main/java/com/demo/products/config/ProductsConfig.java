package com.demo.products.config;

import com.demo.products.dto.ProductsContactInfoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductsConfig {

    @Bean
    public ProductsContactInfoDto productsContactInfoDto() {
        return new ProductsContactInfoDto();
    }
}
