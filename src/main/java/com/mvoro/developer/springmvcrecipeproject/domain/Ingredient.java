package com.mvoro.developer.springmvcrecipeproject.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {

    /**
     * JPA requires no-args constructor for domain objects
     * because it often happens that the JPA provider has to instantiate your domain object dynamically.
     * It cannot do so, unless there is a no-arg constructor - it can't guess what the arguments should be.
     */
    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Id
    private String id;

    private String description;

    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure unitOfMeasure;

}
