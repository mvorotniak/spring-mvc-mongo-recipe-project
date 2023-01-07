package com.mvoro.developer.springmvcrecipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.commands.CategoryCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Category;

import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setName(source.getName());

        return categoryCommand;
    }
}
