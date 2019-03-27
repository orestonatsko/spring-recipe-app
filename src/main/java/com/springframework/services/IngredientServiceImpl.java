package com.springframework.services;

import com.springframework.commands.IngredientCommand;
import com.springframework.converters.IngredientToIngredientCommand;
import com.springframework.domain.Recipe;
import com.springframework.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipeRepository recipeRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (!optionalRecipe.isPresent()) {
            throw new RuntimeException("There is no recipe with id: " + recipeId);
        }
        Optional<IngredientCommand> ingredientOptional = optionalRecipe.get()
                .getIngredients()
                .stream()
                .filter(i -> i.getId().equals(ingredientId))
                .map(i -> ingredientToIngredientCommand.convert(i))
                .findFirst();
        if (!ingredientOptional.isPresent()) {
            throw new RuntimeException("There is no ingredient with id: " + ingredientId);
        }

        return ingredientOptional.get();
    }
}
