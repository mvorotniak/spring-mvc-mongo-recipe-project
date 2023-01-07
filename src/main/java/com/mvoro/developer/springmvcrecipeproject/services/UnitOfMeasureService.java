package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.Set;

import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

public interface UnitOfMeasureService {

    Set<UnitOfMeasure> findAll();

    Set<UnitOfMeasureCommand> findAllCommand();

    UnitOfMeasureCommand findCommandById(String id);
}
