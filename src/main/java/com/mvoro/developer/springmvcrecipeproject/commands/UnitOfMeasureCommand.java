package com.mvoro.developer.springmvcrecipeproject.commands;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {

    private String id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;

}
