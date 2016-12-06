package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.utils.ParamsHelper;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {
    public static final int FILTER_REQUEST_CODE = 0;
    private Fragment fragment;
    public final ObservableInt progressBarVisibility;
    public final ObservableInt searchLabelVisibility;
    public final ObservableInt filterButtonVisibility;
    public final ObservableField<String> searchText;
    public final ObservableField<String> searchLabelText;
    private OnRecipesLoadedListener onRecipesLoadedListener;
    private Map<String, String> searchParams;
    private DialogFragment filterFragment;

    public SearchViewModel(@NonNull Fragment fragment, @NonNull OnRecipesLoadedListener onRecipesLoadedListener) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        progressBarVisibility = new ObservableInt(View.INVISIBLE);
        searchLabelVisibility = new ObservableInt(View.VISIBLE);
        filterButtonVisibility = new ObservableInt(View.INVISIBLE);
        searchText = new ObservableField<>();
        searchLabelText = new ObservableField<>(fragment.getString(R.string.label_search));
        searchParams = new ArrayMap<>();
        filterFragment = new FilterDialogFragment();
    }

    public void searchRecipes(String searchText, boolean forceUpdate) {
        if (forceUpdate || !(this.searchText.get().toLowerCase().equals(searchText.toLowerCase())))
            repository.setCacheIsDirty();

        searchParams.put(ParameterKeys.QUERY, searchText);
        subscriptions.clear();

        progressBarVisibility.set(View.VISIBLE);
        searchLabelVisibility.set(View.INVISIBLE);

        Subscription subscription = repository.searchRecipes(searchParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("RF", "onCompleted()");
                        progressBarVisibility.set(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 11/25/16 show errors
                        progressBarVisibility.set(View.INVISIBLE);
                        Log.e("RF", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        String commonSearchLabelText = fragment.getString(R.string.label_search);
                        String nothingFoundText = fragment.getString(R.string.label_search_nothing_found);
                        searchLabelText.set(recipes.isEmpty() ? nothingFoundText : commonSearchLabelText);
                        searchLabelVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        filterButtonVisibility.set(recipes.isEmpty() ? View.INVISIBLE : View.VISIBLE);
                        onRecipesLoadedListener.onRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);
    }

    public void loadMoreRecipes() {
        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.QUERY, this.searchText.get().toLowerCase());
        subscriptions.clear();
        progressBarVisibility.set(View.VISIBLE);

        Subscription subscription = repository.loadMore(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("RF", "onCompleted()");
                        progressBarVisibility.set(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 11/25/16 show errors
                        progressBarVisibility.set(View.INVISIBLE);
                        Log.d("RF", "onError()");
                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        onRecipesLoadedListener.onMoreRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);

    }

    public void onApplyFilterOptions(Bundle filterData) {
        String healthLabel = ParamsHelper.formatLabel(filterData.getString(FilterDialogFragment.HEALTH_LABEL_KEY));
        String dietLabel = ParamsHelper.formatLabel(filterData.getString(FilterDialogFragment.DIET_LABEL_KEY));

        searchParams.put(ParameterKeys.HEALTH, healthLabel);
        searchParams.put(ParameterKeys.DIET, dietLabel);

        searchRecipes(this.searchText.get().toLowerCase(), true);
    }

    public void onFilterClick(View view) {
        filterFragment.setTargetFragment(fragment, FILTER_REQUEST_CODE);
        filterFragment.show(fragment.getActivity().getSupportFragmentManager(), "Filter");
    }

    public void onTextChanged(CharSequence text, int start, int before, int count) {
        searchText.set(text.toString());
    }

}
