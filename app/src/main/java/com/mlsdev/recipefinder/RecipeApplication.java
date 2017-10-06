package com.mlsdev.recipefinder;

import android.app.Activity;
import android.app.Application;

import com.mlsdev.recipefinder.di.ApplicationInjector;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class RecipeApplication extends Application implements HasActivityInjector {
    protected static RecipeApplication instance;

    public static RecipeApplication getInstance() {
        return instance;
    }

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ApplicationInjector.init(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
