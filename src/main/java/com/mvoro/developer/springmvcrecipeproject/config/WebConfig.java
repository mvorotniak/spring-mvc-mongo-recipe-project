package com.mvoro.developer.springmvcrecipeproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import static org.springframework.web.servlet.function.RequestPredicates.GET;

@Configuration
public class WebConfig {

    @Bean
    public RouterFunction<?> routes(RecipeService recipeService) {
        return RouterFunctions.route(GET("/api/recipes"),
            serverRequest -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipeService.getAllRecipes().collectList().block()));
    }

}
