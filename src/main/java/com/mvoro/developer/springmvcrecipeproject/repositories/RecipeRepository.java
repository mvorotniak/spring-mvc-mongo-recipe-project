package com.mvoro.developer.springmvcrecipeproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
