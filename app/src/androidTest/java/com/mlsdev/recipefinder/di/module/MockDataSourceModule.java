package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.local.LocalDataSource;
import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;
import com.mlsdev.recipefinder.data.source.remote.RemoteDataSource;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import org.mockito.Mockito;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockDataSourceModule {
    public static final String LOCAL_DATA_SOURCE = "local_data_source";
    public static final String REMOTE_DATA_SOURCE = "remote_data_source";

    @Provides
    @Singleton
    DataRepository provideDataRepository(@Named(LOCAL_DATA_SOURCE) DataSource local,
                                         @Named(REMOTE_DATA_SOURCE) DataSource remote) {
        return Mockito.mock(DataRepository.class);
    }

    @Provides
    @Singleton
    @Named(LOCAL_DATA_SOURCE)
    DataSource provideLocalDataSource(AppDatabase database) {
        return Mockito.mock(LocalDataSource.class);
    }

    @Provides
    @Singleton
    @Named(REMOTE_DATA_SOURCE)
    DataSource provideRemoteDataSource() {
        return Mockito.mock(RemoteDataSource.class);
    }

}
