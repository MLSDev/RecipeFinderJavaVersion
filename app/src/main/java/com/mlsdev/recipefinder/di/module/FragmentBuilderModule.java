package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.view.analysenutrition.ingredient.IngredientAnalysisFragment;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisFragment;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoriteRecipesFragment;
import com.mlsdev.recipefinder.view.searchrecipes.SearchRecipeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract SearchRecipeFragment contributeSearchRecipeFragment();

    @ContributesAndroidInjector
    abstract FavoriteRecipesFragment contributeFavoriteRecipesFragment();

    @ContributesAndroidInjector
    abstract IngredientAnalysisFragment contributeIngredientAnalysisFragment();

    @ContributesAndroidInjector
    abstract RecipeAnalysisFragment contributeRecipeAnalysisFragment();

}
