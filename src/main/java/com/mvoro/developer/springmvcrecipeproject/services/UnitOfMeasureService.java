package com.mvoro.developer.springmvcrecipeproject.services;

import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasure> findAll();

    Flux<UnitOfMeasureCommand> findAllCommand();

    Mono<UnitOfMeasureCommand> findCommandById(String id);
}
