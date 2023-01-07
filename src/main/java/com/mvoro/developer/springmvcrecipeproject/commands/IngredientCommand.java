package com.mvoro.developer.springmvcrecipeproject.commands;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private String id;

    private String recipeId;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255) // Max value of characters in Hibernate by default is 255
    private String description;

    @NotNull
    private BigDecimal amount;

    private UnitOfMeasureCommand unitOfMeasureCommand;

}
