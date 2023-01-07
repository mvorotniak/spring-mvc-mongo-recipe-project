package com.mvoro.developer.springmvcrecipeproject.repositories.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mvoro.developer.springmvcrecipeproject.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepositoryIT {

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void init() {
        this.categoryReactiveRepository.deleteAll().block();
    }

    @Test
    void saveCategory() {
        final Category category = new Category();
        category.setName("Breakfast");

        this.categoryReactiveRepository.save(category).block();

        assertThat(this.categoryReactiveRepository.count().block()).isEqualTo(1L);
    }

    @Test
    void findCategory() {
        final Category category = new Category();
        category.setName("Breakfast");

        this.categoryReactiveRepository.save(category).block();
        final Category foo = this.categoryReactiveRepository.findAll()
            .filter(c -> category.getName().equals("foo"))
            .blockFirst();

        final Category breakfast = this.categoryReactiveRepository.findAll()
            .filter(c -> category.getName().equals("Breakfast"))
            .blockFirst();

        assertThat(this.categoryReactiveRepository.count().block()).isEqualTo(1L);
        assertThat(foo).isNull();
        assertThat(breakfast).isNotNull();
    }

}
