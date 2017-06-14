package com.mlsdev.recipefinder.data.source.remote;

import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SearchRecipesService {

    @GET(PathConstants.SEARCH)
    Single<SearchResult> searchRecipes(@QueryMap Map<String, String> params);

}
