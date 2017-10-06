package com.mlsdev.recipefinder;

import com.mlsdev.recipefinder.di.MockApplicationComponent;
import com.mlsdev.recipefinder.di.MockApplicationInjector;

public class MockApp extends RecipeApplication {
    private MockApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = MockApplicationInjector.init(this);
    }

    public MockApplicationComponent getComponent() {
        return component;
    }
}
