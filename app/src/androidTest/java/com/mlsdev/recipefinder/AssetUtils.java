package com.mlsdev.recipefinder;

import android.content.Context;

import com.google.gson.Gson;
import com.mlsdev.recipefinder.data.entity.recipe.Hit;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetUtils {

    public static SearchResult getSearchResult(Context context) {
        return new Gson().fromJson(getSearchResultJsonData(context), SearchResult.class);
    }

    public static List<Recipe> getRecipeList(Context context) {
        return getResipesFromSearchResult(getSearchResult(context));
    }

    public static String getSearchResultJsonData(Context context) {
        return getJsonStringFromAssets(context, "search_result.json");
    }

    public static String getEmptySearchResultJsonData(Context context) {
        return getJsonStringFromAssets(context, "search_result_empty.json");
    }

    public static String getMoreRecipesJsonData(Context context) {
        return getJsonStringFromAssets(context, "more_items_search_result.json");
    }

    public static List<Recipe> getMoreRecipeList(Context context) {
        return getResipesFromSearchResult(new Gson().fromJson(getMoreRecipesJsonData(context), SearchResult.class));
    }

    public static Recipe getRecipeEntity(Context context) {
        return new Gson().fromJson(getJsonStringFromAssets(context, "recipe.json"), Recipe.class);
    }

    public static String getIngredientAnalysisJsonData(Context context) {
        return getJsonStringFromAssets(context, "ingredient_analysis.json");
    }

    public static String getRecipeAnalysisJsonData(Context context) {
        return getJsonStringFromAssets(context, "recipe_analysis.json");
    }

    private static List<Recipe> getResipesFromSearchResult(SearchResult searchResult) {
        List<Recipe> recipes = new ArrayList<>();
        for (Hit hit : searchResult.getHits())
            recipes.add(hit.getRecipe());
        return recipes;
    }

    private static String getJsonStringFromAssets(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}
