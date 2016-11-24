package com.mlsdev.recipefinder.data.source.remote;

import android.support.v4.util.ArrayMap;

import com.mlsdev.recipefinder.data.entity.SearchResult;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiClient {
    private String baseUrl;
    private SearchRecipesService searchRecipesService;

    public ApiClient() {
        baseUrl = PathConstants.BASE_URL;
        initApiServices();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private void initApiServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        searchRecipesService = retrofit.create(SearchRecipesService.class);
    }

    public Subscription searchRecipes(Subscriber<SearchResult> subscriber) {
        Map<String, String> params = new ArrayMap<>();
        return searchRecipesService.searchRecipes(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
