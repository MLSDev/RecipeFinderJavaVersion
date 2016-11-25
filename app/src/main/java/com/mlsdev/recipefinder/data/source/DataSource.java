package com.mlsdev.recipefinder.data.source;

import com.mlsdev.recipefinder.data.entity.SearchResult;

import java.util.Map;

import rx.Observable;

public interface DataSource {
    Observable<SearchResult> searchRecipes(Map<String, String> params);
}
