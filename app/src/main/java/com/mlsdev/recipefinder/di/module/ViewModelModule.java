package com.mlsdev.recipefinder.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mlsdev.recipefinder.view.analysenutrition.ingredient.IngredientAnalysisViewModel;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisViewModel;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoritesViewModel;
import com.mlsdev.recipefinder.view.recipedetails.RecipeViewModel;
import com.mlsdev.recipefinder.view.searchrecipes.SearchViewModel;
import com.mlsdev.recipefinder.view.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindFavoritesViewModel(FavoritesViewModel favoritesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(IngredientAnalysisViewModel.class)
    abstract ViewModel bindIngredientAnalysisViewModel(IngredientAnalysisViewModel ingredientAnalysisViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecipeAnalysisViewModel.class)
    abstract ViewModel bindRecipeAnalysisViewModel(RecipeAnalysisViewModel recipeAnalysisViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel.class)
    abstract ViewModel bindRecipeViewModel(RecipeViewModel recipeViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
