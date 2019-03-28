package com.springframework.services;

import com.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
