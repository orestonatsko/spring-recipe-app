package com.springframework.controllers;

import com.springframework.commands.IngredientCommand;
import com.springframework.services.IngredientService;
import com.springframework.services.RecipeService;
import com.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredients of recipe with id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String ingredientByRecipeIdAndIngredientId(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndIngredientId(recipeId, ingredientId));

        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand sourceCommand, @PathVariable Long recipeId) {
        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(sourceCommand);
        return "redirect:/" + ingredientCommand.getRecipeId() + "/ingredient/" + ingredientCommand.getId();
    }


    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredientById(@PathVariable Long recipeId, @PathVariable Long ingredientId){
        ingredientService.deleteIngredientById(recipeId, ingredientId);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }


}
