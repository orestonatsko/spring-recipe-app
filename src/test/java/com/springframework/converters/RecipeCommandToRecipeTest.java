package com.springframework.converters;

import com.springframework.commands.RecipeCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeCommandToRecipeTest {

    private RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {

        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject(){
       assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject(){
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
    }
}