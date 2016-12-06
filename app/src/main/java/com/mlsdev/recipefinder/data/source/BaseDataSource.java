package com.mlsdev.recipefinder.data.source;

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
    public List<Recipe> getFavorites() {
        return new ArrayList<>();
    }

    @Override
    public boolean addToFavorites(Recipe favoriteRecipe) {
        return false;
    }

    @Override
    public boolean removeFromFavorites(Recipe removedRecipe) {
        return false;
    }

    @Override
    public boolean isInFavorites(Recipe recipe) {
        return false;
    }
}
