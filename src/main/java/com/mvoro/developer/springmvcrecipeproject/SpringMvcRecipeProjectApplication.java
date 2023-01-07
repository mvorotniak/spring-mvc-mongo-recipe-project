package com.mvoro.developer.springmvcrecipeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication
public class SpringMvcRecipeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcRecipeProjectApplication.class, args);
    }

}
