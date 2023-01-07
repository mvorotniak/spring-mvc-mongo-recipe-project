package com.mvoro.developer.springmvcrecipeproject.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.Category;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

}
