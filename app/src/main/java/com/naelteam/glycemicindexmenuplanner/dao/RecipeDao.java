package com.naelteam.glycemicindexmenuplanner.dao;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.naelteam.glycemicindexmenuplanner.model.Ingredient;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.Recipe;
import com.naelteam.glycemicindexmenuplanner.model.Unit;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDbKeys;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDbManager;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class RecipeDao implements CouchDbKeys{

    private final static String TAG = RecipeDao.class.getSimpleName();

    public static final String NAME = "name";
    public static final String COOKINGTIME = "cookingtime";
    public static final String PREPTIME = "preptime";
    public static final String INSTRUCTIONS = "instructions";
    public static final String NOTES = "notes";
    public static final String SERVINGS = "servings";
    public static final String REFERENCE = "reference";
    public static final String VIDEOLINK = "videolink";
    public static final String UNIT = "unit";
    public static final String AMOUNT = "amount";
    public static final String INGREDIENTS = "ingredients";
    public static final String PRODUCTID = "productid";

    private CouchDbManager dbManager = CouchDbManager.getInstance();

    public List<Recipe> listAllRecipes(){
        Log.d(TAG, "List all recipes");
        long timeIn = System.currentTimeMillis();

        try {
            QueryEnumerator enumerator = dbManager.list();
            List<Recipe> recipes = new ArrayList<>();
            while (enumerator.hasNext()){
                QueryRow queryRow = enumerator.next();
                Map<String, Object> properties = (Map) queryRow.getValue();
                final Recipe recipe = loadRecipeFromProperties(properties);
                recipes.add(recipe);

                dbManager.getDocumentAttachments(recipe.getId());
            }
            Log.d(TAG, "List all recipes size = " + recipes.size() + ", done in " + (System.currentTimeMillis() - timeIn) + " ms");
            return recipes;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on list all recipes", e);
        }
        return null;
    }

    public Recipe fetchRecipe(String id){
        Map<String, Object> properties = dbManager.getDocument(id);
        return loadRecipeFromProperties(properties);
    }

    public boolean insertRecipe(Recipe recipe) {
        try {
            Map<String, Object> properties = storeProperties(recipe);

            Map<String, Object> attachments = null;
            if (recipe.getPicture() != null) {
                attachments = new HashMap<String, Object>();
                attachments.put("picture", recipe.getPicture());
            }

            String[] doc = dbManager.insert(properties, attachments);
            recipe.setId(doc[0]);
            recipe.setRevId(doc[1]);

            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on insert recipe", e);
        }
        return false;
    }

    public boolean updateRecipe(Recipe recipe) {
        try {
            Map<String, Object> properties = storeProperties(recipe);
            dbManager.update(recipe.getId(), properties, null);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on update recipe", e);
        }
        return false;
    }

    public boolean deleteRecipe(Recipe recipe) {
        try {
            return dbManager.delete(recipe.getId());
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Unexpected error on delete recipe", e);
        }
        return false;
    }

    private Recipe loadRecipeFromProperties(Map<String, Object> properties){
        Recipe recipe = new Recipe();
        recipe.setId((String) properties.get(ID));
        recipe.setRevId((String) properties.get(REV_ID));
        recipe.setName((String) properties.get(NAME));
        recipe.setCookingTime((Integer) properties.get(COOKINGTIME));
        recipe.setPrepTime((Integer) properties.get(PREPTIME));
        recipe.setInstructions((String) properties.get(INSTRUCTIONS));
        recipe.setNotes((String) properties.get(NOTES));
        recipe.setServings((Integer) properties.get(SERVINGS));
        recipe.setReference((String) properties.get(REFERENCE));
        recipe.setVideoLink((String) properties.get(VIDEOLINK));

        Object ingredientsObj = properties.get(INGREDIENTS);
        if (ingredientsObj != null){
            final List<Map<String, Object>> ingredientsProperties = (List<Map<String, Object>>) ingredientsObj;

            List<Ingredient> ingredients = null;
            if (ingredientsProperties != null){
                ingredients = new ArrayList<>();
                for (Map<String, Object> ingredientsProperty:ingredientsProperties){
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName((String) ingredientsProperty.get(NAME));
                    ingredient.setUnit(Unit.valueOf((String) ingredientsProperty.get(UNIT)));
                    ingredient.setAmount((String) ingredientsProperty.get(AMOUNT));
                    ingredient.setNotes((String) ingredientsProperty.get(NOTES));
                    ingredient.setProductId((String) ingredientsProperty.get(PRODUCTID));
                    ingredients.add(ingredient);
                }
            }
            if (ingredients != null){
                recipe.setIngredients(ingredients);
            }
        }
        return recipe;
    }

    private Map<String, Object> storeProperties(Recipe recipe){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(NAME, recipe.getName());
        properties.put(COOKINGTIME, recipe.getCookingTime());
        properties.put(PREPTIME, recipe.getPrepTime());
        properties.put(INSTRUCTIONS, recipe.getInstructions());
        properties.put(NOTES, recipe.getNotes());
        properties.put(SERVINGS, recipe.getServings());
        properties.put(REFERENCE, recipe.getReference());
        properties.put(VIDEOLINK, recipe.getVideoLink());

        List<Map<String, Object>> subPropertiesList = null;
        final List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients !=null && ingredients.size() > 0) {
            subPropertiesList = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                Map<String, Object> subProperties = new HashMap<String, Object>();
                subProperties.put(NAME, ingredient.getName());
                subProperties.put(UNIT, ingredient.getUnit());
                subProperties.put(AMOUNT, ingredient.getAmount());
                subProperties.put(NOTES, ingredient.getNotes());
                subProperties.put(PRODUCTID, ingredient.getProductId());
                subPropertiesList.add(subProperties);
            }
            if (subPropertiesList!=null){
                properties.put(INGREDIENTS, subPropertiesList);
            }
        }
        return properties;
    }

}
