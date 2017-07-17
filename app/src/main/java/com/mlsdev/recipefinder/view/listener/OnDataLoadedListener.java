package com.mlsdev.recipefinder.view.listener;

public interface OnDataLoadedListener<T> {
    void onDataLoaded(T recipes);

    void onMoreDataLoaded(T moreRecipes);
}
