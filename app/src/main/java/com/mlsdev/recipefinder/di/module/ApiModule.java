package com.mlsdev.recipefinder.di.module;

import com.google.gson.Gson;
import com.mlsdev.recipefinder.BuildConfig;
import com.mlsdev.recipefinder.data.source.remote.NutritionAnalysisService;
import com.mlsdev.recipefinder.data.source.remote.PathConstants;
import com.mlsdev.recipefinder.data.source.remote.SearchRecipesService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    public static final String HTTP_LOGGING = "http_logging_interceptor";
    public String baseUrl = PathConstants.BASE_URL;

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Named(HTTP_LOGGING)
    Interceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@Named(HTTP_LOGGING) Interceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    SearchRecipesService provideSearchRecipesService(Retrofit retrofit) {
        return retrofit.create(SearchRecipesService.class);
    }

    @Provides
    @Singleton
    NutritionAnalysisService provideNutritionAnalysisService(Retrofit retrofit) {
        return retrofit.create(NutritionAnalysisService.class);
    }

}
