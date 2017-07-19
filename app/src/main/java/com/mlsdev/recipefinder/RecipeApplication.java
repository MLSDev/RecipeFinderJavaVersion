package com.mlsdev.recipefinder;

import android.app.Application;

import com.mlsdev.recipefinder.di.component.ApplicationComponent;
import com.mlsdev.recipefinder.di.component.DaggerApplicationComponent;
import com.mlsdev.recipefinder.di.module.ApplicationModule;
import com.mlsdev.recipefinder.di.module.UtilsModule;

public class RecipeApplication extends Application {
    private static ApplicationComponent applicationComponent;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = buildApplicationComponent();
    }

    private ApplicationComponent buildApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .utilsModule(new UtilsModule())
                .build();
    }

}
