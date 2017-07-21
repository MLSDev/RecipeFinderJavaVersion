package com.mlsdev.recipefinder.di.component;

import android.app.Application;

import com.mlsdev.recipefinder.RecipeApplication;
import com.mlsdev.recipefinder.di.module.ApiModule;
import com.mlsdev.recipefinder.di.module.DataSourceModule;
import com.mlsdev.recipefinder.di.module.DatabaseModule;
import com.mlsdev.recipefinder.di.module.MainActivityModule;
import com.mlsdev.recipefinder.di.module.RecipeAnalysisActivityModule;
import com.mlsdev.recipefinder.di.module.UtilsModule;
import com.mlsdev.recipefinder.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        UtilsModule.class,
        DataSourceModule.class,
        DatabaseModule.class,
        ApiModule.class,
        MainActivityModule.class,
        RecipeAnalysisActivityModule.class,
        ViewModelModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(RecipeApplication application);

}
