package com.mlsdev.recipefinder.data.source.remote;

import android.support.v4.util.ArrayMap;

import com.mlsdev.recipefinder.BuildConfig;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource extends BaseDataSource implements DataSource {
    private static String baseUrl = PathConstants.BASE_URL;
    private SearchRecipesService searchRecipesService;
    private NutritionAnalysisService nutritionAnalysisService;
    private static RemoteDataSource instance;

    private RemoteDataSource() {
        initApiServices();
    }

    public static RemoteDataSource getInstance() {
        if (instance == null)
            instance = new RemoteDataSource();

        return instance;
    }

    public static void setBaseUrl(String url) {
        baseUrl = url;
        instance = null;
    }

    private void initApiServices() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Response response = chain.proceed(chain.request());
//                        if (response != null && response.body() != null)
//                            Log.d("TEST", response.body().string());
//                        return response;
//                    }
//                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        searchRecipesService = retrofit.create(SearchRecipesService.class);
        nutritionAnalysisService = retrofit.create(NutritionAnalysisService.class);
    }

    @Override
    public Single<SearchResult> searchRecipes(Map<String, String> params) {
        setCredentials(params, true);
        return searchRecipesService.searchRecipes(params);
    }

    @Override
    public Single<NutritionAnalysisResult> getIngredientData(Map<String, String> params) {
        setCredentials(params, false);
        return nutritionAnalysisService.analyzeIngredient(params);
    }

    @Override
    public Single<NutritionAnalysisResult> getRecipeAnalysingResult(RecipeAnalysisParams params) {
        Map<String, String> credentials = new ArrayMap<>();
        setCredentials(credentials, false);
        return nutritionAnalysisService.analyzeRecipe(params, credentials);
    }

    private void setCredentials(Map<String, String> params, boolean search) {
        params.put(ParameterKeys.APP_ID, search ? BuildConfig.SEARCH_APP_ID : BuildConfig.ANALYSE_APP_ID);
        params.put(ParameterKeys.APP_KEY, search ? BuildConfig.SEARCH_APP_KEY : BuildConfig.ANALYSE_APP_KEY);
    }
}
