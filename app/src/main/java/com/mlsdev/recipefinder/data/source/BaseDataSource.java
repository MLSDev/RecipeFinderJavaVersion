package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.data.entity.SearchResult;

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
    public Observable<List<Recipe>> getFavorites() {
        return Observable.from(new ArrayList<Recipe>()).toList();
    }

    @Override
    public Observable<Boolean> addToFavorites(Recipe favoriteRecipe) {
        return Observable.from(new Boolean[]{});
    }

    @Override
    public Observable<Boolean> removeFromFavorites(Recipe removedRecipe) {
        return Observable.from(new Boolean[]{});
    }
}
