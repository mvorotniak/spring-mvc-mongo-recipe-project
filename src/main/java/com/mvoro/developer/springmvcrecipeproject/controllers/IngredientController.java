package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.services.IngredientService;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;
import com.mvoro.developer.springmvcrecipeproject.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredientsByRecipe(@PathVariable final String id, final Model model) {
        final RecipeCommand recipeCommand = recipeService.findCommandById(id).block();
        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipe_id") final String recipeId, @PathVariable final String id,
        final Model model) {

        model.addAttribute("ingredient", ingredientService.findCommandByRecipeAndIngredientId(recipeId, id).block());

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable("recipe_id") final String recipeId, @PathVariable final String id,
        final Model model) {

        final IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeAndIngredientId(recipeId, id).block();
        ingredientCommand.setRecipeId(recipeId);
        final List<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.findAllCommand()
            .collectList()
            .block();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommandSet);

        return "recipe/ingredient/form";
    }

    @PostMapping("/recipe/{id}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute("ingredient") @Valid final IngredientCommand ingredientCommand,
        final BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "recipe/ingredient/form";
        }

        final IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/ingredient/new")
    public String createNewIngredient(@PathVariable String id, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(id);

        List<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.findAllCommand()
            .collectList()
            .block();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommandSet);

        return "recipe/ingredient/form";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable("recipe_id") String recipeId, @PathVariable String id) {
        log.info("Deleting ingredient with id {}...", id);
        ingredientService.deleteByRecipeAndIngredientId(recipeId, id).block();

        // Redirect to list of ingredients
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
