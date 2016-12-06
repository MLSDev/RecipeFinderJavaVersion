package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.util.List;
import java.util.Map;

import rx.Observable;

public interface DataSource {
    Observable<SearchResult> searchRecipes(Map<String, String> params);
    List<Recipe> getFavorites();
    boolean addToFavorites(Recipe favoriteRecipe);
    boolean removeFromFavorites(Recipe removedRecipe);
    boolean isInFavorites(Recipe recipe);
}
