package com.mlsdev.recipefinder.data.source.remote;

import com.mlsdev.recipefinder.data.entity.nutrition.IngredientAnalysisResult;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface NutritionAnalysisService {

    @GET(PathConstants.NUTRITION_DATA)
    Observable<IngredientAnalysisResult> getIngredientData(@QueryMap Map<String, String> params);

}
