package com.naelteam.glycemicindexmenuplanner.dao;

import android.test.AndroidTestCase;
import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.Ingredient;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Recipe;
import com.naelteam.glycemicindexmenuplanner.model.Unit;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDbManager;

import java.util.List;

/**
 *
 */
public class RecipeDaoTest extends AndroidTestCase {

    RecipeDao sut;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CouchDbManager.getInstance().init(getContext());
        sut = new RecipeDao();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        CouchDbManager.getInstance().destroy();
    }

    public void testListRecipes(){
        Recipe recipe = createRecipe();
        sut.insertRecipe(recipe);

        Recipe recipe2 = createRecipe();
        recipe2.setName("Recipe2title");
        sut.insertRecipe(recipe2);

        List<Recipe> recipes = sut.listAllRecipes();
        Log.d("", "recipes size = " + recipes.size());
    }

    private Recipe createRecipe(){
        Recipe recipe = new Recipe();
        recipe.setName("Recipe1title");
        recipe.setCookingTime(15);
        recipe.setPrepTime(10);
        recipe.setServings(4);
        recipe.setInstructions("Instructions necessaires");
        recipe.setVideoLink("http://www.cooking.com/roti.mp4");
        recipe.setReference("http://www.cooking.com");
        recipe.setNotes("Notes indispensables");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("ingredient1Name");
        ingredient1.setNotes("notes indispensables pour ingredient");
        ingredient1.setAmount("150");
        ingredient1.setUnit(Unit.POUND);
        ingredient1.setProductId("123445646-75454575");
        recipe.addIngredient(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("ingredient2Name");
        ingredient2.setNotes("notes indispensables pour ingredient2");
        ingredient2.setAmount("1502");
        ingredient2.setUnit(Unit.POUND);
        ingredient2.setProductId("123445646-754545752");
        recipe.addIngredient(ingredient2);

        return recipe;
    }

    public void testInsertRecipe(){
        Recipe recipe = createRecipe();
        sut.insertRecipe(recipe);

        Recipe otherRecipe = sut.fetchRecipe(recipe.getId());
        assertRecipe(otherRecipe, recipe);

        recipe.setName("title2");
        sut.updateRecipe(recipe);

        Recipe otherRecipe2 = sut.fetchRecipe(recipe.getId());
        assertRecipe(otherRecipe2, recipe);
    }

    private void assertRecipe(Recipe recipe, Recipe otherRecipe){
        assertEquals(otherRecipe.getCookingTime(), recipe.getCookingTime());
        assertEquals(otherRecipe.getPrepTime(), recipe.getPrepTime());
        assertEquals(otherRecipe.getNotes(), recipe.getNotes());
        assertEquals(otherRecipe.getServings(), recipe.getServings());
        assertEquals(otherRecipe.getInstructions(), recipe.getInstructions());
        assertEquals(otherRecipe.getReference(), recipe.getReference());

        assertEquals(otherRecipe.getIngredients().size(), recipe.getIngredients().size());

        int count=0;
        for(Ingredient ingredient:otherRecipe.getIngredients()){
            assertEquals(ingredient.getName(), recipe.getIngredients().get(count).getName());
            assertEquals(ingredient.getNotes(), recipe.getIngredients().get(count).getNotes());
            assertEquals(ingredient.getAmount(), recipe.getIngredients().get(count).getAmount());
            assertEquals(ingredient.getUnit(), recipe.getIngredients().get(count).getUnit());
            assertEquals(ingredient.getProductId(), recipe.getIngredients().get(count).getProductId());
            count++;
        }

    }

    public void testDeleteDatabase() throws Exception{
        CouchDbManager.getInstance().init(getContext());
        assertTrue(CouchDbManager.getInstance().deleteCurrentDatabase());
    }
}
