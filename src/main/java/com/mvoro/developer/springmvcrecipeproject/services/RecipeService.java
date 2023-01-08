package com.mvoro.developer.springmvcrecipeproject.services;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> getAllRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);

    Mono<Void> deleteById(String id);
}
