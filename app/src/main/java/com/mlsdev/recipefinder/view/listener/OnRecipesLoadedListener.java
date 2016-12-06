package com.mlsdev.recipefinder.view.listener;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;

import java.util.List;

public interface OnRecipesLoadedListener {
    void onRecipesLoaded(List<Recipe> recipes);

    void onMoreRecipesLoaded(List<Recipe> moreRecipes);
}
