package com.mvoro.developer.springmvcrecipeproject.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity classes have different requirements than plain Java classes.
 * That makes Lombok’s generated equals() and hashCode() methods unusable.
 * You can use the @Getter, @Setter, and @Builder annotation without breaking your application.
 * The only Lombok annotations you need to avoid are @Data, @ToString, and @EqualsAndHashCode.
 */
@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;

    private Note note;

    private Set<Ingredient> ingredients = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    private String source;

    private String url;

    private String directions;

    private Difficulty difficulty;

    private Byte[] image;

    public void setNote(Note note) {
        this.note = note;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

}
