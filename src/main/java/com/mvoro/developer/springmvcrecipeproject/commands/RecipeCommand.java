package com.mvoro.developer.springmvcrecipeproject.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mvoro.developer.springmvcrecipeproject.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private String id;

    private NoteCommand noteCommand;

    private Set<IngredientCommand> ingredientCommands = new HashSet<>();

    private Set<CategoryCommand> categoryCommands = new HashSet<>();

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer prepTime;

    @NotNull
    @Min(1)
    @Max(999)
    private Integer cookTime;

    @NotNull
    private Integer servings;

    private String source;

    @URL
    private String url;

    private String directions;

    private Difficulty difficulty;

    private Byte[] image;

}
