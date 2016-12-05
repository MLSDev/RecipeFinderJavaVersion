package com.mlsdev.recipefinder.view.favoriterecipes;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

public class FavoritesViewModel extends BaseViewModel {
    private OnRecipesLoadedListener onRecipesLoadedListener;

    public FavoritesViewModel(Context context, OnRecipesLoadedListener onRecipesLoadedListener) {
        super(context);
        this.onRecipesLoadedListener = onRecipesLoadedListener;
    }

    public void getFavoriteRecipes() {
        List<Recipe> recipes = repository.getFavoriteRecipes();
        onRecipesLoadedListener.onRecipesLoaded(recipes);
    }
}
