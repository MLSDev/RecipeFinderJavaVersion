package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecipeAnalysisActivityModule {
    @ContributesAndroidInjector
    abstract RecipeAnalysisDetailsActivity contributeActivity();
}
