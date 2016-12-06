package com.mlsdev.recipefinder.data.source.remote;

import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SearchRecipesService {

    @GET(PathConstants.SEARCH)
    Observable<SearchResult> searchRecipes(@QueryMap Map<String, String> params);

}
