package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import com.mvoro.developer.springmvcrecipeproject.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceIml implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceIml(
        UnitOfMeasureRepository unitOfMeasureRepository,
        UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand
    ) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasure> findAll() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(unitOfMeasures::add);

        return unitOfMeasures;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAllCommand() {
        return findAll().stream()
            .map(unitOfMeasureToUnitOfMeasureCommand::convert)
            .collect(Collectors.toSet());
    }

    @Override
    public UnitOfMeasureCommand findCommandById(String id) {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findById(id);

        return unitOfMeasureOptional.map(unitOfMeasureToUnitOfMeasureCommand::convert).orElse(null);
    }
}
