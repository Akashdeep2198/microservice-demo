package com.demo.ribbon;

import com.demo.ribbon.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@RibbonClient(name = "products", configuration = RibbonConfiguration.class)
public class ProductComposite {
	public static void main(String[] args) {
		SpringApplication.run(ProductComposite.class, args);
	}
}
