package com.springframework.services;

import com.springframework.commands.RecipeCommand;
import com.springframework.converters.RecipeToRecipeCommand;
import com.springframework.domain.Recipe;
import com.springframework.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceImplIT {

    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    @Transactional
    public void saveRecipeCommand() {
        //given
        Iterable<Recipe> recipeIterable = recipeRepository.findAll();
        Recipe recipe = recipeIterable.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        assertNotNull(recipeCommand);

        //when
        recipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(recipe.getId(), recipeCommand.getId());
        assertEquals(recipe.getCategories().size(), recipeCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), recipeCommand.getIngredients().size());


    }
}