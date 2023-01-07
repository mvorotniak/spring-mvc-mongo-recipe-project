package com.mvoro.developer.springmvcrecipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.commands.CategoryCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Category;

import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        Category category = new Category();
        category.setId(source.getId());
        category.setName(source.getName());

        return category;
    }
}
