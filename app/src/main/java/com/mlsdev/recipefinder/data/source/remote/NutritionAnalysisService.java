package com.mlsdev.recipefinder.data.source.remote;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface NutritionAnalysisService {

    @GET(PathConstants.NUTRITION_DATA)
    Single<NutritionAnalysisResult> analyzeIngredient(@QueryMap Map<String, String> params);

    @POST(PathConstants.NUTRITION_DETAILS)
    Single<NutritionAnalysisResult> analyzeRecipe(@Body RecipeAnalysisParams params,
                                                      @QueryMap Map<String, String> credentials);

}
