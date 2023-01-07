package com.mvoro.developer.springmvcrecipeproject.bootstrap;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.domain.Category;
import com.mvoro.developer.springmvcrecipeproject.domain.Difficulty;
import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import com.mvoro.developer.springmvcrecipeproject.exceptions.NotFoundException;
import com.mvoro.developer.springmvcrecipeproject.repositories.CategoryRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("!dev & !pro")
public class RecipesInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final RecipeRepository recipeRepository;

    // Constructor injection is recommended over field injection
    public RecipesInitializer(
        CategoryRepository categoryRepository,
        UnitOfMeasureRepository unitOfMeasureRepository,
        RecipeRepository recipeRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Populating MongoDB...");
        loadCategories();
        loadUom();
        recipeRepository.saveAll(getRecipes());
        log.info("Finished populating MongoDB");
    }

    private void loadCategories(){
        Category breakfast = new Category();
        breakfast.setName("Breakfast");
        categoryRepository.save(breakfast);
    }

    private void loadUom(){
        UnitOfMeasure teaspoon = new UnitOfMeasure();
        teaspoon.setDescription("Teaspoon");
        unitOfMeasureRepository.save(teaspoon);

        UnitOfMeasure tablespoon = new UnitOfMeasure();
        tablespoon.setDescription("Tablespoon");
        unitOfMeasureRepository.save(tablespoon);

        UnitOfMeasure cup = new UnitOfMeasure();
        cup.setDescription("Cup");
        unitOfMeasureRepository.save(cup);
    }

    private Set<Recipe> getRecipes() {
        Category breakfast =  categoryRepository.findByName("Breakfast")
            .orElseThrow(() -> new NotFoundException("Unable to find category 'Breakfast'"));

        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon")
            .orElseThrow(() -> new NotFoundException("Unable to find unit of measure 'Teaspoon'"));
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon")
            .orElseThrow(() -> new NotFoundException("Unable to find unit of measure 'Tablespoon'"));
        UnitOfMeasure cup = unitOfMeasureRepository.findByDescription("Cup")
            .orElseThrow(() -> new NotFoundException("Unable to find unit of measure 'Cup'"));

        Note pumpkinNote = new Note();
        pumpkinNote.setNote("These Pumpkin Chocolate Chip Muffins combine cozy pumpkin spice with pockets of rich chocolate. They’re an easy way to add some much-needed spiced comfort to chilly mornings.");

        Note smoothieNote = new Note();
        smoothieNote.setNote("Blend up a big glass of this refreshing Cantaloupe Smoothie for a quick cool down. Ripe pieces of juicy melon make the perfect light and sweet base for the chilly beverage.");

        Recipe pumpkin = new Recipe();
        pumpkin.setDescription("Pumpkin Chocolate Chip Muffins");
        pumpkin.setPrepTime(20);
        pumpkin.setCookTime(20);
        pumpkin.setServings(12);
        pumpkin.setSource("simplyrecipes.com");
        pumpkin.setUrl("https://www.simplyrecipes.com/pumpkin-chocolate-chip-muffins-recipe-5206559");
        pumpkin.setDirections("Bakery");
        pumpkin.setDifficulty(Difficulty.MODERATE);
        pumpkin.addIngredient(new Ingredient("Flour", BigDecimal.valueOf(2), teaspoon));
        pumpkin.addIngredient(new Ingredient("Pumpkin pie spice", BigDecimal.valueOf(1), teaspoon));
        pumpkin.addIngredient(new Ingredient("Baking powder", BigDecimal.valueOf(1.5), tablespoon));
        pumpkin.setNote(pumpkinNote);

        Recipe smoothie = new Recipe();
        smoothie.setDescription("Cantaloupe Smoothie");
        smoothie.setPrepTime(5);
        smoothie.setCookTime(5);
        smoothie.setServings(1);
        smoothie.setSource("simplyrecipes.com");
        smoothie.setUrl("https://www.simplyrecipes.com/cantaloupe-smoothie-recipe-5204176");
        smoothie.setDirections("Drinks");
        smoothie.setDifficulty(Difficulty.EASY);
        smoothie.addIngredient(new Ingredient("Cantaloupe", BigDecimal.valueOf(0.5), cup));
        smoothie.addIngredient(new Ingredient("Greek yogurt", BigDecimal.valueOf(0.25), cup));
        smoothie.addIngredient(new Ingredient("Pineapple juice", BigDecimal.valueOf(0.5), cup));
        smoothie.setNote(smoothieNote);

        // Recipe-Category Many to Many relationship
        pumpkin.getCategories().add(breakfast);
        smoothie.getCategories().add(breakfast);

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(pumpkin);
        recipes.add(smoothie);

        log.info("Returning " + recipes.size() + " recipes...");

        return recipes;
    }
}
