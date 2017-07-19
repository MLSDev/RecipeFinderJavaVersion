package com.mlsdev.recipefinder.di.component;

import android.content.Context;

import com.mlsdev.recipefinder.di.module.ApplicationModule;
import com.mlsdev.recipefinder.di.module.UtilsModule;
import com.mlsdev.recipefinder.view.analysenutrition.ingredient.IngredientAnalysisFragment;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisDetailsActivity;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisFragment;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoriteRecipesFragment;
import com.mlsdev.recipefinder.view.searchrecipes.SearchRecipeFragment;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;
import com.mlsdev.recipefinder.view.utils.ParamsHelper;
import com.mlsdev.recipefinder.view.utils.UtilsUI;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, UtilsModule.class})
public interface ApplicationComponent {

    Context context();

    DiagramUtils diagramUtils();

    UtilsUI utilsUI();

    ParamsHelper paramsHelper();

    void inject(SearchRecipeFragment fragment);

    void inject(FavoriteRecipesFragment fragment);

    void inject(IngredientAnalysisFragment fragment);

    void inject(RecipeAnalysisFragment fragment);

    void inject(RecipeAnalysisDetailsActivity activity);

}
