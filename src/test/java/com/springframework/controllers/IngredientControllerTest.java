package com.springframework.controllers;

import com.springframework.commands.IngredientCommand;
import com.springframework.commands.RecipeCommand;
import com.springframework.services.IngredientService;
import com.springframework.services.RecipeService;
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

    private static final Long ID = 1L;
    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    private IngredientController ingredientController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void listIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/" + ID + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void ingredientByRecipeIdAndIngredientId() throws Exception {
        //given
        IngredientCommand recipeCommand = new IngredientCommand();

        //when
        when(ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        //then
        verify(ingredientService, times(1))
                .findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());

    }
}