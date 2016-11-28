package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.ObservableField;

import com.mlsdev.recipefinder.data.entity.Recipe;

public class RecipeListItemViewModel {
    public final ObservableField<String> recipeTitle;
    public final ObservableField<String> recipeImageUrl;

    public RecipeListItemViewModel(Recipe recipe) {
        recipeTitle = new ObservableField<>(recipe.getLabel());
        recipeImageUrl = new ObservableField<>(recipe.getImage());
    }

    public void setRecipe(Recipe recipe) {
        recipeTitle.set(recipe.getLabel());
        recipeImageUrl.set(recipe.getImage());
    }
}
