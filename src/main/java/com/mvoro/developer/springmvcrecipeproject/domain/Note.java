package com.mvoro.developer.springmvcrecipeproject.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    @Id
    private String id;

    private String note;

}
