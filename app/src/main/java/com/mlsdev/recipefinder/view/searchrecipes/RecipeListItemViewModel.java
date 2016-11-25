package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.ObservableField;

import com.mlsdev.recipefinder.data.entity.Recipe;

public class RecipeListItemViewModel {
    public final ObservableField<String> recipeTitle;

    public RecipeListItemViewModel(Recipe recipe) {
        recipeTitle = new ObservableField<>(recipe.getLabel());
    }

    public void setRecipe(Recipe recipe) {
        recipeTitle.set(recipe.getLabel());
    }
}
