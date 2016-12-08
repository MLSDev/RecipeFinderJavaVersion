package com.mlsdev.recipefinder.view.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import rx.subscriptions.CompositeSubscription;

public class BaseViewModel {
    protected Context context;
    protected DataRepository repository;
    protected CompositeSubscription subscriptions;
    public final ObservableInt loadContentProgressBarVisibility;

    public BaseViewModel(Context context) {
        this.context = context;
        repository = DataRepository.getInstance(context);
        subscriptions = new CompositeSubscription();
        loadContentProgressBarVisibility = new ObservableInt(View.INVISIBLE);
    }

    public void onDestroy(){
        subscriptions.clear();
    }
}
