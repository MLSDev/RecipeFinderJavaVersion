package com.mlsdev.recipefinder.data.source.remote;

import android.content.Context;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.IngredientAnalysisResult;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;
import com.mlsdev.recipefinder.data.source.DataSource;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RemoteDataSource extends BaseDataSource implements DataSource {
    private Context context;
    private static String baseUrl;
    private SearchRecipesService searchRecipesService;
    private NutritionAnalysisService nutritionAnalysisService;

    public RemoteDataSource(Context context) {
        this.context = context;
        baseUrl = PathConstants.BASE_URL;
        initApiServices();
    }

    public static void setBaseUrl(String url) {
        baseUrl = url;
    }

    private void initApiServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        searchRecipesService = retrofit.create(SearchRecipesService.class);
        nutritionAnalysisService = retrofit.create(NutritionAnalysisService.class);
    }

    @Override
    public Observable<SearchResult> searchRecipes(Map<String, String> params) {
        setCredentials(params, true);
        return searchRecipesService.searchRecipes(params);
    }

    @Override
    public Observable<IngredientAnalysisResult> getIngredientData(Map<String, String> params) {
        setCredentials(params, false);
        return nutritionAnalysisService.getIngredientData(params);
    }

    private void setCredentials(Map<String, String> params, boolean search) {
        String appId = context.getString(search ? R.string.search_app_id : R.string.analyse_app_id);
        String appKey = context.getString(search ? R.string.search_app_key : R.string.analyse_app_key);

        params.put(ParameterKeys.APP_ID, appId);
        params.put(ParameterKeys.APP_KEY, appKey);
    }
}
