package com.mvoro.developer.springmvcrecipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.commands.NoteCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

import lombok.Synchronized;

import static com.mvoro.developer.springmvcrecipeproject.utils.ConverterUtils.verifyNotNullOrEmpty;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;

    private final NoteToNoteCommand noteToNoteCommand;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public RecipeToRecipeCommand(
        CategoryToCategoryCommand categoryToCategoryCommand,
        NoteToNoteCommand noteToNoteCommand,
        IngredientToIngredientCommand ingredientToIngredientCommand
    ) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.noteToNoteCommand = noteToNoteCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Synchronized
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setImage(source.getImage());

        NoteCommand noteCommand = noteToNoteCommand.convert(source.getNote());
        recipeCommand.setNoteCommand(noteCommand);

        if (verifyNotNullOrEmpty(source.getCategories())) {
            source.getCategories()
                .forEach(category -> recipeCommand.getCategoryCommands().add(categoryToCategoryCommand.convert(category)));
        }

        if (verifyNotNullOrEmpty(source.getIngredients())) {
            source.getIngredients()
                .forEach(ingredient -> recipeCommand.getIngredientCommands().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
