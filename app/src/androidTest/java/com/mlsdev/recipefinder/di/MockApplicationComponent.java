package com.mlsdev.recipefinder.di;

import android.app.Application;

import com.mlsdev.recipefinder.di.module.MockApiModule;
import com.mlsdev.recipefinder.MockApp;
import com.mlsdev.recipefinder.di.module.MockDataSourceModule;
import com.mlsdev.recipefinder.di.module.DatabaseModule;
import com.mlsdev.recipefinder.di.module.MainActivityModule;
import com.mlsdev.recipefinder.di.module.RecipeAnalysisActivityModule;
import com.mlsdev.recipefinder.di.module.UtilsModule;
import com.mlsdev.recipefinder.di.module.ViewModelModule;
import com.mlsdev.recipefinder.view.analysenutrition.ingredient.IngredientAnalysisFragmentTest;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisFragmentTest;
import com.mlsdev.recipefinder.view.searchrecipes.SearchRecipesFragmentTest;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        UtilsModule.class,
        MockDataSourceModule.class,
        DatabaseModule.class,
        MockApiModule.class,
        MainActivityModule.class,
        RecipeAnalysisActivityModule.class,
        ViewModelModule.class})
public interface MockApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        MockApplicationComponent build();
    }

    void inject(MockApp application);
    void inject(SearchRecipesFragmentTest fragmentTest);
    void inject(IngredientAnalysisFragmentTest fragmentTest);
    void inject(RecipeAnalysisFragmentTest fragmentTest);

}
