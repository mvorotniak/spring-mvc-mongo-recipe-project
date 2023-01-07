package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.Set;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

public interface RecipeService {

    Set<Recipe> getAllRecipes();

    Recipe findById(String id);

    RecipeCommand findCommandById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(String id);
}
