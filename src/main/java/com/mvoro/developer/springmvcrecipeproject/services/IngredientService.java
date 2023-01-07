package com.mvoro.developer.springmvcrecipeproject.services;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandByRecipeAndIngredientId(String recipeId, String id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteByRecipeAndIngredientId(String recipeId, String id);
}
