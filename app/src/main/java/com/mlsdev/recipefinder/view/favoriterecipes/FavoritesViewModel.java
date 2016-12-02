package com.mlsdev.recipefinder.view.favoriterecipes;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoritesViewModel extends BaseViewModel{
    private OnRecipesLoadedListener onRecipesLoadedListener;

    public FavoritesViewModel(Context context, OnRecipesLoadedListener onRecipesLoadedListener) {
        super(context);
        this.onRecipesLoadedListener = onRecipesLoadedListener;
    }

    public void getFavoriteRecipes() {
        Subscription subscription = repository.getFavoriteRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        onRecipesLoadedListener.onRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);
    }
}
