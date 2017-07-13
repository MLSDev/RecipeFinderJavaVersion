package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public abstract class BaseDataSource implements DataSource {
    @Override
    public Single<SearchResult> searchRecipes(Map<String, String> params) {
        return null;
    }

    @Override
    public Single<NutritionAnalysisResult> getIngredientData(Map<String, String> params) {
        return null;
    }

    @Override
    public Flowable<List<Recipe>> getFavorites() {
        return null;
    }

    @Override
    public Completable addToFavorites(Recipe favoriteRecipe) {
        return null;
    }

    @Override
    public Completable removeFromFavorites(Recipe removedRecipe) {
        return null;
    }

    @Override
    public Single<Boolean> isInFavorites(Recipe recipe) {
        return null;
    }

    @Override
    public Single<NutritionAnalysisResult> getRecipeAnalysingResult(RecipeAnalysisParams params) {
        return null;
    }

}
