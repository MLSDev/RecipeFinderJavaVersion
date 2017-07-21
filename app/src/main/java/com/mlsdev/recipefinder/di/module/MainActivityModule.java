package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = {FragmentBuilderModule.class})
    abstract MainActivity contributeMainActivityInjector();
}
