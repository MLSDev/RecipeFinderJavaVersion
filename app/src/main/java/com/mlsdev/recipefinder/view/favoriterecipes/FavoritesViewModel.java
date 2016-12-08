package com.mlsdev.recipefinder.view.favoriterecipes;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesViewModel extends BaseViewModel {
    private OnRecipesLoadedListener onRecipesLoadedListener;
    public final ObservableInt emptyViewVisibility;

    public FavoritesViewModel(Context context, OnRecipesLoadedListener onRecipesLoadedListener) {
        super(context);
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        emptyViewVisibility = new ObservableInt(View.VISIBLE);
    }

    public void getFavoriteRecipes() {
        subscriptions.clear();

        Subscription subscription = repository.getFavoriteRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<Recipe>>() {
                    @Override
                    public void onNext(List<Recipe> recipes) {
                        emptyViewVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        onRecipesLoadedListener.onRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);
    }
}
