package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface DataSource {
    Single<SearchResult> searchRecipes(Map<String, String> params);

    Single<NutritionAnalysisResult> getIngredientData(Map<String, String> params);

    Single<List<Recipe>> getFavorites();

    Single<Boolean> addToFavorites(Recipe favoriteRecipe);

    Single<Boolean> removeFromFavorites(Recipe removedRecipe);

    Single<Boolean> isInFavorites(Recipe recipe);

    Single<NutritionAnalysisResult> getRecipeAnalysingResult(RecipeAnalysisParams params);

    void addAnalyzingResult(NutritionAnalysisResult analysisResult, Map<String, String> params);
}
