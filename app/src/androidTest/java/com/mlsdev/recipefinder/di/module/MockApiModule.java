package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.data.source.remote.NutritionAnalysisService;
import com.mlsdev.recipefinder.data.source.remote.SearchRecipesService;
import com.mlsdev.recipefinder.di.module.ApiModule;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockApiModule extends ApiModule {

    @Provides
    @Singleton
    SearchRecipesService provideSearchRecipesService() {
        return Mockito.mock(SearchRecipesService.class);
    }

    @Provides
    @Singleton
    NutritionAnalysisService provideNutritionAnalysisService() {
        return Mockito.mock(NutritionAnalysisService.class);
    }


}
