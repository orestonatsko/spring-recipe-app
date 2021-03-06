package com.springframework.services;

import com.springframework.commands.IngredientCommand;
import com.springframework.converters.IngredientCommandToIngredient;
import com.springframework.converters.IngredientCommandToUnitOfMeasure;
import com.springframework.converters.IngredientToIngredientCommand;
import com.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.domain.Ingredient;
import com.springframework.domain.Recipe;
import com.springframework.repository.RecipeRepository;
import com.springframework.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 8L;

    private IngredientService ingredientService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository,
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                unitOfMeasureRepository,
                new IngredientCommandToIngredient(new IngredientCommandToUnitOfMeasure()));
    }

    @Test
    public void findIngredientByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findIngredientByRecipeIdAndIngredientId(RECIPE_ID, 2L);
        assertEquals(Long.valueOf(2L), ingredientCommand.getId());
        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());

        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void deleteIngredientById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        recipe.addIngredient(ingredient);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(RECIPE_ID)).thenReturn(optionalRecipe);

        //when
        ingredientService.deleteIngredientById(RECIPE_ID, INGREDIENT_ID);
        Optional<Recipe> recipeWithoutIngredient = recipeRepository.findById(RECIPE_ID);

        //then
        assertNotNull(recipeWithoutIngredient);
        assertEquals(0, recipeWithoutIngredient.get().getIngredients().size());
        verify(recipeRepository, times(2)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());

    }
}