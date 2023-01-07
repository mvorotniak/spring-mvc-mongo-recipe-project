package com.mvoro.developer.springmvcrecipeproject.services;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findCommandByRecipeAndIngredientId(String recipeId, String id);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);

    Mono<Void> deleteByRecipeAndIngredientId(String recipeId, String id);
}
