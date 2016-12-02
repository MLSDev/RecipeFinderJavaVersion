package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.data.entity.SearchResult;

import java.util.List;
import java.util.Map;

import rx.Observable;

public interface DataSource {
    Observable<SearchResult> searchRecipes(Map<String, String> params);
    Observable<List<Recipe>> getFavorites();
    Observable<Boolean> addToFavorites(Recipe favoriteRecipe);
    Observable<Boolean> removeFromFavorites(Recipe removedRecipe);
}
