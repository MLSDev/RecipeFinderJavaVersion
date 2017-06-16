package com.mlsdev.recipefinder.data.source;

import android.util.Log;

import com.mlsdev.recipefinder.view.MainActivity;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements SingleObserver<T> {
    public static final int SERVER_ERROR = 500;

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull T t) {

    }

    @Override
    public void onError(Throwable e) {
        Log.e(MainActivity.LOG_TAG, e.getMessage());
    }

}
