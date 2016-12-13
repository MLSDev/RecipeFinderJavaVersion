package com.mlsdev.recipefinder.data.source.remote;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface NutritionAnalysisService {

    @GET(PathConstants.NUTRITION_DATA)
    Observable<NutritionAnalysisResult> analyzeIngredient(@QueryMap Map<String, String> params);

    @POST(PathConstants.NUTRITION_DATA)
    Observable<NutritionAnalysisResult> analyzeRecipe(@Body RecipeAnalysisParams params,
                                                      @QueryMap Map<String, String> credentials);

}
