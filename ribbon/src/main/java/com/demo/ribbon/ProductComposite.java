package com.demo.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductComposite {
	public static void main(String[] args) {
		SpringApplication.run(ProductComposite.class, args);
	}
}
