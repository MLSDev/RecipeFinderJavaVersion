package com.mlsdev.recipefinder.view.searchrecipes;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchViewModel {
    public final ObservableInt clearSearTextButtonVisibility;
    public final ObservableInt progressBarVisibility;
    public final ObservableField<String> searchText;
    private String searchedText;
    private DataRepository repository;
    private CompositeSubscription subscriptions;
    private OnRecipesLoadedListener onRecipesLoadedListener;

    public SearchViewModel(@NonNull Context context, @NonNull OnRecipesLoadedListener onRecipesLoadedListener) {
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        repository = DataRepository.getInstance(context);
        subscriptions = new CompositeSubscription();
        clearSearTextButtonVisibility = new ObservableInt(View.INVISIBLE);
        progressBarVisibility = new ObservableInt(View.INVISIBLE);
        searchText = new ObservableField<>();
    }

    public void searchRecipes(String searchText, boolean forceUpdate) {
        searchedText = searchText;
        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.QUERY, searchText);
        subscriptions.clear();

        if (forceUpdate)
            repository.setCacheIsDirty();

        progressBarVisibility.set(View.VISIBLE);

        Subscription subscription = repository.searchRecipes(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                        // TODO: 11/25/16 stop progress bar
                        Log.d("RF", "onCompleted()");
                        progressBarVisibility.set(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 11/25/16 show errors
                        Log.d("RF", "onError()");
                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        onRecipesLoadedListener.onRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);
    }

    public void loadMoreRecipes() {
        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.QUERY, searchedText);
        subscriptions.clear();
        progressBarVisibility.set(View.VISIBLE);

        Subscription subscription = repository.loadMore(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                        // TODO: 11/25/16 stop progress bar
                        Log.d("RF", "onCompleted()");
                        progressBarVisibility.set(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 11/25/16 show errors
                        Log.d("RF", "onError()");
                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        onRecipesLoadedListener.onMoreRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);

    }

    public void onDestroy() {
        subscriptions.clear();
    }

    public interface OnRecipesLoadedListener {
        void onRecipesLoaded(List<Recipe> recipes);
        void onMoreRecipesLoaded(List<Recipe> moreRecipes);
    }

    public void onClearSearchTextButtonClick(View view) {
        searchText.set("");
    }

    public void onTextChanged(CharSequence text, int start, int before, int count) {
        clearSearTextButtonVisibility.set(text.toString().isEmpty() ? View.INVISIBLE : View.VISIBLE);
        searchText.set(text.toString());
    }
}
