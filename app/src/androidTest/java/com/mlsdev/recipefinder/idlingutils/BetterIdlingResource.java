package com.mlsdev.recipefinder.idlingutils;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

import rx.plugins.RxJavaPlugins;

public class BetterIdlingResource implements IdlingResource, BetterExecutionBridge {

    private static final boolean isLogged = false;
    private IdlingResource.ResourceCallback cb;
    private Integer idler = 0;

    public BetterIdlingResource() {

        try {
            RxJavaPlugins.getInstance().registerObservableExecutionHook(new BetterExecutionHook(this));
        } catch (Exception e) {
        }
    }

    @Override
    public String getName() {

        return this.getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {

        synchronized (idler) {
            if (isLogged)
                Log.e("LOG", "called isidlenow: " + idler, null);
            return idler == 0;
        }
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {

        if (isLogged)
            Log.e("LOG", "called register Idle: " + idler, null);
        this.cb = resourceCallback;
    }

    @Override
    public void onStart() {

        synchronized (idler) {
            idler++;
            if (isLogged)
                Log.e("LOG", "called onstart: " + idler, null);
        }
    }

    @Override
    public void onError() {

        synchronized (idler) {
            idler--;
            if (isLogged)
                Log.e("LOG", "called onerrror: " + idler, null);
            if (idler == 0 && cb != null) {
                cb.onTransitionToIdle();
            }
        }
    }

    @Override
    public void onEnd() {

        synchronized (idler) {
            idler--;
            if (isLogged)
                Log.e("LOG", "called onend: " + idler, null);
            if (idler == 0 && cb != null) {
                cb.onTransitionToIdle();
            }
        }
    }
}