package com.mlsdev.recipefinder.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.mlsdev.recipefinder.view.BottonNavigationItemSelectedListener;
import com.mlsdev.recipefinder.view.analysenutrition.ingredient.IngredientAnalysisViewModel;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisViewModel;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoritesViewModel;
import com.mlsdev.recipefinder.view.searchrecipes.SearchViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(context);
        } else if (modelClass.isAssignableFrom(BottonNavigationItemSelectedListener.class)) {
            return (T) new BottonNavigationItemSelectedListener();
        } else if (modelClass.isAssignableFrom(FavoritesViewModel.class)){
            return (T) new FavoritesViewModel(context);
        } else if (modelClass.isAssignableFrom(IngredientAnalysisViewModel.class)) {
            return (T) new IngredientAnalysisViewModel(context);
        } else if (modelClass.isAssignableFrom(RecipeAnalysisViewModel.class)) {
            return (T) new RecipeAnalysisViewModel(context);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
