package com.akazimour.cloudgateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestFilterConfig {

    @Autowired
    private TestFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("ORDER-MS", r -> r.path("/api/order/**").and().method("GET","POST","PUT","DELETE").filters(f -> f.filter(filter)).uri("lb://ORDER-MS"))
                .route("CATALOG-MS", r -> r.path("/api/catalog/**").and().method("POST","PUT","DELETE").filters(f -> f.filter(filter)).uri("lb://CATALOG-MS")).build();
    }
}
