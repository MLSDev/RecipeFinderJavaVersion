package com.mlsdev.recipefinder;

import android.app.Application;

import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;

public class RecipeFinderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.init(this);
    }
}
