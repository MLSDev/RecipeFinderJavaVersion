package com.mlsdev.recipefinder.data.source.repository;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.SearchResult;
import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.data.source.remote.RemoteDataSource;

import java.util.Map;

import rx.Observable;
import rx.functions.Action1;

public class DataRepository extends PaginatedDataSource implements DataSource {
    private DataSource remoteDataSource;

    public DataRepository(Context context) {
        remoteDataSource = new RemoteDataSource(context);
    }

    @Override
    public Observable<SearchResult> searchRecipes(Map<String, String> params) {
        params.put(ParameterKeys.FROM, String.valueOf(0));
        params.put(ParameterKeys.TO, String.valueOf(20));
        return remoteDataSource.searchRecipes(params).doOnNext(new Action1<SearchResult>() {
            @Override
            public void call(SearchResult searchResult) {
                DataRepository.this.setupPagination(searchResult.getTo(), searchResult.isMore());
            }
        });
    }
}
