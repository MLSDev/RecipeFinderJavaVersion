package com.mlsdev.recipefinder.view.favoriterecipes;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

public class FavoritesViewModel extends BaseViewModel {
    private OnRecipesLoadedListener onRecipesLoadedListener;
    public final ObservableInt emptyViewVisibility;

    public FavoritesViewModel(Context context, OnRecipesLoadedListener onRecipesLoadedListener) {
        super(context);
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        emptyViewVisibility = new ObservableInt(View.VISIBLE);
    }

    public void getFavoriteRecipes() {
        List<Recipe> recipes = repository.getFavoriteRecipes();
        emptyViewVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        onRecipesLoadedListener.onRecipesLoaded(recipes);
    }
}
