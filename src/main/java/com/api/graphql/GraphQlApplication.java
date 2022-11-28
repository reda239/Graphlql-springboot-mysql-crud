package com.api.graphql;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;



@SpringBootApplication
@EnableR2dbcRepositories
public class GraphQlApplication {

    public static void main(String[] args) {

        SpringApplication.run(GraphQlApplication.class, args);

    }

}


