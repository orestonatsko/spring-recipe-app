package com.springframework.controllers;

import com.springframework.commands.IngredientCommand;
import com.springframework.commands.RecipeCommand;
import com.springframework.converters.IngredientToIngredientCommand;
import com.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.services.IngredientService;
import com.springframework.services.RecipeService;
import com.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 8L;
    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientController ingredientController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void listIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(INGREDIENT_ID);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/" + INGREDIENT_ID + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void ingredientByRecipeIdAndIngredientId() throws Exception {
        //given
        IngredientCommand recipeCommand = new IngredientCommand();

        when(ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredients/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1))
                .findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());

    }


    @Test
    public void deleteIngredientById() throws Exception {


        //when
        mockMvc.perform(get("/recipe/" + RECIPE_ID + "/ingredient/" + INGREDIENT_ID + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/" + RECIPE_ID + "/ingredients"));
        //then
        verify(ingredientService, times(1)).deleteIngredientById(RECIPE_ID, INGREDIENT_ID);

    }
}