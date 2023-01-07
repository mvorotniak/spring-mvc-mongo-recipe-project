package com.mvoro.developer.springmvcrecipeproject.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
