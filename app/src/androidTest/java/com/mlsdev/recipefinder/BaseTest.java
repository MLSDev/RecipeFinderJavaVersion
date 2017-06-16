package com.mlsdev.recipefinder;

import com.mlsdev.recipefinder.data.source.remote.RemoteDataSource;
import com.mlsdev.recipefinder.idlingutils.RxIdlingResource;

import java.io.IOException;

import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;

class BaseTest {
    MockWebServer mockWebServer;
    private RxIdlingResource rxIdlingResource;

    public void setUp() throws IOException {
        rxIdlingResource = new RxIdlingResource();
        registerIdlingResources(rxIdlingResource);
        RxJavaPlugins.setScheduleHandler(rxIdlingResource);
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        RemoteDataSource.setBaseUrl(mockWebServer.url("/").url().toString());
    }

    public void tearDown() throws IOException {
        unregisterIdlingResources(rxIdlingResource);
        rxIdlingResource = null;
        mockWebServer.shutdown();
    }

}
