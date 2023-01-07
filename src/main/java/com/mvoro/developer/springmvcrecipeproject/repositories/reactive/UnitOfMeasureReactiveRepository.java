package com.mvoro.developer.springmvcrecipeproject.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

}
