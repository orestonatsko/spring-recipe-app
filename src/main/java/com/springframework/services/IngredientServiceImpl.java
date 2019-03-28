package com.springframework.services;

import com.springframework.commands.IngredientCommand;
import com.springframework.converters.IngredientCommandToIngredient;
import com.springframework.converters.IngredientToIngredientCommand;
import com.springframework.domain.Ingredient;
import com.springframework.domain.Recipe;
import com.springframework.repository.RecipeRepository;
import com.springframework.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipeRepository recipeRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            return new IngredientCommand();
        }
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientCommand.getId()))
                .findFirst();
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UnitOfMeasure Not Found!")));

        } else {
            recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientCommand.getId()))
                .findFirst().get());
    }
}
