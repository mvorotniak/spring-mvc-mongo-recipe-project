package com.mvoro.developer.springmvcrecipeproject.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
