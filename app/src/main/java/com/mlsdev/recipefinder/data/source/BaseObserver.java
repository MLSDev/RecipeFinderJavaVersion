package com.mlsdev.recipefinder.data.source;

import android.util.Log;

import com.mlsdev.recipefinder.view.MainActivity;

import rx.Observer;

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e(MainActivity.LOG_TAG, e.getMessage());
    }

    @Override
    public void onNext(T data) {
    }
}
