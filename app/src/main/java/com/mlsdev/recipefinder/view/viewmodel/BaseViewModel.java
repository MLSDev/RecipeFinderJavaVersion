package com.mlsdev.recipefinder.view.viewmodel;

import android.content.Context;

import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import rx.subscriptions.CompositeSubscription;

public class BaseViewModel {
    protected Context context;
    protected DataRepository repository;
    protected CompositeSubscription subscriptions;

    public BaseViewModel(Context context) {
        this.context = context;
        repository = DataRepository.getInstance(context);
        subscriptions = new CompositeSubscription();
    }

    public void onDestroy(){
        subscriptions.clear();
    }
}
