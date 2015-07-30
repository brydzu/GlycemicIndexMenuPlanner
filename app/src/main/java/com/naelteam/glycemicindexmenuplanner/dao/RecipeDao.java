package com.naelteam.glycemicindexmenuplanner.dao;

import com.couchbase.lite.CouchbaseLiteException;
import com.naelteam.glycemicindexmenuplanner.model.Ingredient;
import com.naelteam.glycemicindexmenuplanner.model.Recipe;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDBManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fg on 30/07/15.
 */
public class RecipeDao {

    private CouchDBManager dbManager = CouchDBManager.getInstance();

    public void insertRecipe(Recipe recipe) throws CouchbaseLiteException {

        Map<String, Object> properties = storeProperties(recipe);

        Map<String, Object> attachments = null;
        if (recipe.getPicture() != null) {
            attachments = new HashMap<String, Object>();
            attachments.put("picture", recipe.getPicture());
        }

        dbManager.insert(properties, attachments);
    }

    private Map<String, Object> storeProperties(Recipe recipe){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", recipe.getName());
        properties.put("cookingtime", recipe.getCookingTime());
        properties.put("preptime", recipe.getPrepTime());
        properties.put("instructions", recipe.getInstructions());
        properties.put("notes", recipe.getNotes());
        properties.put("servings", recipe.getServings());
        properties.put("reference", recipe.getReference());
        properties.put("videolink", recipe.getVideoLink());

        for (Ingredient ingredient:recipe.getIngredients()){
            Map<String, Object> subProperties = new HashMap<String, Object>();
            subProperties.put("name", ingredient.getName());
            subProperties.put("unit", ingredient.getUnit());
            subProperties.put("amount", ingredient.getAmount());
            subProperties.put("otherinfo", ingredient.getOtherInfo());
            properties.put("ingredients", subProperties);
        }
        return properties;
    }

}
