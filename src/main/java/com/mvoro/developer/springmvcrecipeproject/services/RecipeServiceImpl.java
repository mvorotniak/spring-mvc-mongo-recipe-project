package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.converters.RecipeCommandToRecipe;
import com.mvoro.developer.springmvcrecipeproject.converters.RecipeToRecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.exceptions.NotFoundException;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Layer for Recipes
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(
        RecipeRepository recipeRepository,
        RecipeToRecipeCommand recipeToRecipeCommand,
        RecipeCommandToRecipe recipeCommandToRecipe
    ) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe not found by ID " + id);
        }

        return recipe.get();
    }

    @Override
    // Marking as transactional as we're making a conversion outside the scope. Avoiding issues that might be cause because of lazy loading.
    @Transactional
    public RecipeCommand findCommandById(String id) {
        Recipe recipe = findById(id);

        return recipeToRecipeCommand.convert(recipe);
    }

    // We want this method to be executed in a transaction
    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        if (recipe == null) {
            log.error("Unable to save null recipe...");
            return null;
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        log.debug("Saved recipe with id {}", recipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);

        log.debug("Deleted recipe with id {}", id);
    }
}
