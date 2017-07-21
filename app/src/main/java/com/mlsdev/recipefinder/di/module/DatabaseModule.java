package com.mlsdev.recipefinder.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), AppDatabase.class, "recipes.db")
                .build();
    }

}
