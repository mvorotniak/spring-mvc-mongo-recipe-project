package com.mvoro.developer.springmvcrecipeproject.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.converters.RecipeCommandToRecipe;
import com.mvoro.developer.springmvcrecipeproject.converters.RecipeToRecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.repositories.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Layer for Recipes
 */
@Slf4j
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    @Override
    public Flux<Recipe> getAllRecipes() {
        return this.recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return this.recipeReactiveRepository.findById(id);
    }

    @Override
    // Marking as transactional as we're making a conversion outside the scope. Avoiding issues that might be cause because of lazy loading.
    @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {
        return findById(id).mapNotNull(this.recipeToRecipeCommand::convert);
    }

    // We want this method to be executed in a transaction
    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        if (recipe == null) {
            log.error("Unable to save null recipe...");
            return null;
        }

        Mono<RecipeCommand> savedRecipe = this.recipeReactiveRepository.save(recipe)
            .mapNotNull(this.recipeToRecipeCommand::convert);

        log.debug("Saved recipe with id {}", recipe.getId());
        return savedRecipe;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        this.recipeReactiveRepository.deleteById(id).block();

        log.debug("Deleted recipe with id {}", id);

        return Mono.empty();
    }
}
