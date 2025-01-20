package com.demo.ribbon.config;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.PingUrl;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IClientConfig ribbonClientConfig() {
        return IClientConfig.Builder.newBuilder().build();
    }

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
