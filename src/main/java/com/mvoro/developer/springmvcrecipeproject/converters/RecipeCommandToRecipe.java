package com.mvoro.developer.springmvcrecipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

import lombok.Synchronized;

import static com.mvoro.developer.springmvcrecipeproject.utils.ConverterUtils.verifyNotNullOrEmpty;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;

    private final NoteCommandToNote noteCommandToNote;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public RecipeCommandToRecipe(
        CategoryCommandToCategory categoryCommandToCategory,
        NoteCommandToNote noteCommandToNote,
        IngredientCommandToIngredient ingredientCommandToIngredient
    ) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.noteCommandToNote = noteCommandToNote;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    // Making the method thread safe
    @Synchronized
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setServings(source.getServings());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDirections(source.getDirections());
        recipe.setImage(source.getImage());

        Note note = noteCommandToNote.convert(source.getNoteCommand());
        recipe.setNote(note);

        if (verifyNotNullOrEmpty(source.getCategoryCommands())) {
            source.getCategoryCommands()
                .forEach(categoryCommand -> recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        }

        if (verifyNotNullOrEmpty(source.getIngredientCommands())) {
            source.getIngredientCommands()
                .forEach(ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }

        return recipe;
    }

}
