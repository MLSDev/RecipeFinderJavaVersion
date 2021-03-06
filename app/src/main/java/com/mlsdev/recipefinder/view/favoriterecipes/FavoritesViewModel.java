package com.mlsdev.recipefinder.view.favoriterecipes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableInt;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;
import com.mlsdev.recipefinder.view.listener.OnDataLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesViewModel extends BaseViewModel implements LifecycleObserver {
    private OnDataLoadedListener<List<Recipe>> onDataLoadedListener;
    public final ObservableInt emptyViewVisibility;

    @Inject
    public FavoritesViewModel(DataRepository repository) {
        this.repository = repository;
        emptyViewVisibility = new ObservableInt(View.VISIBLE);
    }

    public void setOnDataLoadedListener(OnDataLoadedListener<List<Recipe>> onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        getFavoriteRecipes();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
    }

    public void getFavoriteRecipes() {
        Disposable disposable = repository.getFavoriteRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(@NonNull List<Recipe> recipes) throws Exception {
                        emptyViewVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        onDataLoadedListener.onDataLoaded(recipes);
                    }
                });

        subscriptions.add(disposable);
    }
}
