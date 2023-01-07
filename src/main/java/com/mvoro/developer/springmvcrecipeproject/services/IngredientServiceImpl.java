package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.converters.IngredientCommandToIngredient;
import com.mvoro.developer.springmvcrecipeproject.converters.IngredientToIngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import com.mvoro.developer.springmvcrecipeproject.exceptions.NotFoundException;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.reactive.RecipeReactiveRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final RecipeReactiveRepository recipeReactiveRepository;

    private final RecipeRepository recipeRepository;

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @Override
    public Mono<IngredientCommand> findCommandByRecipeAndIngredientId(String recipeId, String id) {
        return this.recipeReactiveRepository.findById(recipeId)
            .map(recipe -> recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst())
            .filter(Optional::isPresent)
            .mapNotNull(ingredient -> {
                IngredientCommand ingredientCommand = this.ingredientToIngredientCommand.convert(ingredient.get());
                ingredientCommand.setRecipeId(recipeId);

                return ingredientCommand;
            });
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(final IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(ingredientCommand.getRecipeId());

        Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientCommand.getId()))
                .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient i = ingredientOptional.get();
                updateIngredient(i, ingredientCommand);
            } else {
                recipe.getIngredients().add(ingredient);
            }

            Recipe savedRecipe = this.recipeReactiveRepository.save(recipe).block();

            return Optional.ofNullable(savedRecipe)
                .flatMap(r -> r.getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredient.getId()))
                    .map(i -> {
                        IngredientCommand command = this.ingredientToIngredientCommand.convert(i);
                        command.setRecipeId(ingredientCommand.getRecipeId());

                        return command;
                    })
                    .findFirst())
                .map(Mono::just)
                .orElse(Mono.empty());
        } else {
            throw new NotFoundException(
                String.format("Recipe with id=[%s] was not found in Database.", ingredientCommand.getRecipeId()));
        }
    }

    @Override
    public Mono<Void> deleteByRecipeAndIngredientId(String recipeId, String id) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(id));
            this.recipeReactiveRepository.save(recipe).block();
            log.debug("Deleted ingredient with id {}", id);
        }

        return Mono.empty();
    }

    private void updateIngredient(Ingredient fromIngredient, IngredientCommand toIngredientCommand) {
        fromIngredient.setDescription(toIngredientCommand.getDescription());
        fromIngredient.setAmount(toIngredientCommand.getAmount());

        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository
            .findById(toIngredientCommand.getUnitOfMeasureCommand().getId()).block();
        if (unitOfMeasure != null) {
            fromIngredient.setUnitOfMeasure(unitOfMeasure);
        }
    }
}
