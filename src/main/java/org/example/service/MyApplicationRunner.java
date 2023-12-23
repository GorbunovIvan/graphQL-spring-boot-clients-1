package org.example.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.RSocketGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component that will be called (void run method) by Spring Boot immediately after loading.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    private final HttpGraphQlClient httpGraphQlClient;
    private final RSocketGraphQlClient rSocketGraphQlClient;

    public MyApplicationRunner(HttpGraphQlClient httpGraphQlClient, RSocketGraphQlClient rSocketGraphQlClient) {
        this.httpGraphQlClient = httpGraphQlClient;
        this.rSocketGraphQlClient = rSocketGraphQlClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        queryGreetingHttp();
//        queryGreetingRSocket(); // doesn't work
        queryBooksHttp();
    }

    public void queryGreetingHttp() {

        var httpRequestDocument = """
                 {
                    hello
                 }
                """;

        httpGraphQlClient.document(httpRequestDocument)
                .retrieve("hello")
                .toEntity(String.class)
                .subscribe(System.out::println);
    }

    public void queryGreetingRSocket() {

        var rSocketRequestDocument = """
                 subscription {
                    hello
                 }
                """;

        rSocketGraphQlClient.document(rSocketRequestDocument)
                .retrieveSubscription("hello")
                .toEntity(String.class)
                .subscribe(System.out::println);
    }

    public void queryBooksHttp() {

        var httpRequestDocument = """
                 {
                    books {
                        id
                        name
                    }
                 }
                """;

        httpGraphQlClient.document(httpRequestDocument)
                .retrieve("books")
                .toEntity(new ParameterizedTypeReference<List<Book>>() {})
                .subscribe(System.out::println);
    }
}

record Book(int id, String name) {}