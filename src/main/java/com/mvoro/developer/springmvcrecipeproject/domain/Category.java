package com.mvoro.developer.springmvcrecipeproject.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Category {

    @Id
    private String id;

    private String name;

    private Set<Recipe> recipes;

}
