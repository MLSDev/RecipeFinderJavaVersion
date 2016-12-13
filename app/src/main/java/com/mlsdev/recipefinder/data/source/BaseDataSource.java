package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;

public abstract class BaseDataSource implements DataSource {
    @Override
    public Observable<SearchResult> searchRecipes(Map<String, String> params) {
        return Observable.from(new SearchResult[]{});
    }

    @Override
    public Observable<NutritionAnalysisResult> getIngredientData(Map<String, String> params) {
        return null;
    }

    @Override
    public Observable<List<Recipe>> getFavorites() {
        return Observable.from(new ArrayList<List<Recipe>>());
    }

    @Override
    public Observable<Boolean> addToFavorites(Recipe favoriteRecipe) {
        return Observable.from(new Boolean[]{false});
    }

    @Override
    public Observable<Boolean> removeFromFavorites(Recipe removedRecipe) {
        return Observable.from(new Boolean[]{false});
    }

    @Override
    public Observable<Boolean> isInFavorites(Recipe recipe) {
        return Observable.from(new Boolean[]{false});
    }

    @Override
    public Observable<NutritionAnalysisResult> getRecipeAnalysingResult(RecipeAnalysisParams params) {
        return null;
    }

    @Override
    public void addAnalyzingResult(NutritionAnalysisResult analysisResult, Map<String, String> params) {
    }
}
