package com.mvoro.developer.springmvcrecipeproject.services;

import org.springframework.stereotype.Service;

import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import com.mvoro.developer.springmvcrecipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class UnitOfMeasureServiceIml implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Flux<UnitOfMeasure> findAll() {
        return this.unitOfMeasureReactiveRepository.findAll();
    }

    @Override
    public Flux<UnitOfMeasureCommand> findAllCommand() {
        return findAll()
            .mapNotNull(this.unitOfMeasureToUnitOfMeasureCommand::convert);
    }

    @Override
    public Mono<UnitOfMeasureCommand> findCommandById(String id) {
        return this.unitOfMeasureReactiveRepository.findById(id)
            .mapNotNull(this.unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
