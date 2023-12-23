package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.RSocketGraphQlClient;

/**
 * Run "graphQL-spring-boot-queries" project first
 */
@Configuration
public class GraphQLClientConfig {

    @Bean
    public HttpGraphQlClient httpGraphQlClient() {
        return HttpGraphQlClient.builder()
                .url("http://localhost:8080/graphql")
                .build();
    }

    @Bean
    public RSocketGraphQlClient rSocketGraphQlClient(RSocketGraphQlClient.Builder<?> builder) {
        return builder
                .tcp("localhost", 8080)
                .route("graphql")
                .build();
    }
}
