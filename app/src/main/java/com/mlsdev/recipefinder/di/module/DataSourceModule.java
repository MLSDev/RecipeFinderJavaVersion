package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.local.LocalDataSource;
import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;
import com.mlsdev.recipefinder.data.source.remote.RemoteDataSource;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {
    public static final String LOCAL_DATA_SOURCE = "local_data_source";
    public static final String REMOTE_DATA_SOURCE = "remote_data_source";

    @Provides
    @Singleton
    DataRepository provideDataRepository(@Named(LOCAL_DATA_SOURCE) DataSource local,
                                         @Named(REMOTE_DATA_SOURCE) DataSource remote) {
        return new DataRepository(local, remote);
    }

    @Provides
    @Singleton
    @Named(LOCAL_DATA_SOURCE)
    DataSource provideLocalDataSource(AppDatabase database) {
        return new LocalDataSource(database);
    }

    @Provides
    @Singleton
    @Named(REMOTE_DATA_SOURCE)
    DataSource provideRemoteDataSource() {
        return new RemoteDataSource();
    }

}
