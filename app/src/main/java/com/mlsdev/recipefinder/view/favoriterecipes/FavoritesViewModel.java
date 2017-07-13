package com.mlsdev.recipefinder.view.favoriterecipes;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesViewModel extends BaseViewModel {
    private OnRecipesLoadedListener onRecipesLoadedListener;
    public final ObservableInt emptyViewVisibility;

    public FavoritesViewModel(Context context, OnRecipesLoadedListener onRecipesLoadedListener) {
        super(context);
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        emptyViewVisibility = new ObservableInt(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    public void getFavoriteRecipes() {
        Disposable disposable = repository.getFavoriteRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(@NonNull List<Recipe> recipes) throws Exception {
                        emptyViewVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        onRecipesLoadedListener.onRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(disposable);
    }
}
