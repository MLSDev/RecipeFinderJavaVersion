package com.mlsdev.recipefinder.view.searchrecipes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.claudiodegio.msv.OnSearchViewListener;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.utils.ParamsHelper;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel implements OnSearchViewListener, LifecycleObserver {
    public final ObservableInt loadMoreProgressBarVisibility = new ObservableInt(View.INVISIBLE);
    public final ObservableInt searchLabelVisibility = new ObservableInt(View.VISIBLE);
    public final ObservableField<String> searchText = new ObservableField<>();
    public final ObservableField<String> searchLabelText;
    private OnRecipesLoadedListener onRecipesLoadedListener;
    private Map<String, String> searchParams;
    private ActionListener actionListener;
    public final ObservableBoolean isSearchOpened = new ObservableBoolean(false);
    private String query = "";
    private List<Recipe> recipes = new ArrayList<>();

    public SearchViewModel(@NonNull Context context) {
        super(context);
        searchLabelText = new ObservableField<>(context.getString(R.string.label_search));
        searchParams = new ArrayMap<>();
        keyboardListener = actionListener;
    }

    public void setOnRecipesLoadedListener(OnRecipesLoadedListener onRecipesLoadedListener) {
        this.onRecipesLoadedListener = onRecipesLoadedListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void start() {
        populateRecipeList();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Log.d("RF", "lifecycle stop");
    }

    private void populateRecipeList() {
        if (!recipes.isEmpty()) {
            onRecipesLoadedListener.onRecipesLoaded(recipes);
            searchLabelVisibility.set(View.INVISIBLE);
        }
    }

    /**
     * @param searchText  The search phrase inputted by a user
     * @param forceUpdate A force update flag. If it is "true" cache will be cleaned, else a user will
     *                    see cached data.
     */
    public void searchRecipes(String searchText, boolean forceUpdate) {
        if (searchText == null || searchText.isEmpty()) {
            onRecipesLoadedListener.onRecipesLoaded(new ArrayList<Recipe>());
            return;
        }

        query = searchText;

        String prevSearchText = searchParams.containsKey(ParameterKeys.QUERY) ? searchParams.get(ParameterKeys.QUERY) : "";

        if (forceUpdate || !(prevSearchText.equals(searchText.toLowerCase())))
            repository.setCacheIsDirty();

        searchParams.put(ParameterKeys.QUERY, query.toLowerCase());
        subscriptions.clear();
        searchLabelVisibility.set(View.INVISIBLE);
        repository.searchRecipes(searchParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SearchRecipesObserver<List<Recipe>>() {
                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        showProgressDialog(false, null);
                        SearchViewModel.this.recipes = recipes;
                        String commonSearchLabelText = context.getString(R.string.label_search);
                        String nothingFoundText = context.getString(R.string.label_search_nothing_found);
                        searchLabelText.set(recipes.isEmpty() ? nothingFoundText : commonSearchLabelText);
                        searchLabelVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        populateRecipeList();
                    }
                });
    }

    public void loadMoreRecipes() {
        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.QUERY, query.toLowerCase());
        subscriptions.clear();
        loadMoreProgressBarVisibility.set(View.VISIBLE);

        repository.loadMore(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SearchRecipesObserver<List<Recipe>>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull List<Recipe> recipes) {
                        onRecipesLoadedListener.onMoreRecipesLoaded(recipes);
                        SearchViewModel.this.recipes.addAll(recipes);
                    }
                });

    }

    public void refresh() {
        searchRecipes(query, true);
    }

    /**
     * Makes a search with applied searching parameters
     *
     * @param filterData The {@link Bundle} object with applied parameters for searching
     */
    public void onApplyFilterOptions(Bundle filterData) {
        String healthLabel = ParamsHelper.formatLabel(filterData.getString(FilterDialogFragment.HEALTH_LABEL_KEY));
        String dietLabel = ParamsHelper.formatLabel(filterData.getString(FilterDialogFragment.DIET_LABEL_KEY));

        searchParams.put(ParameterKeys.HEALTH, healthLabel);
        searchParams.put(ParameterKeys.DIET, dietLabel);

        searchRecipes(query, true);
    }

    public void onFilterClick(View view) {
        actionListener.onStartFilter();
    }


    @Override
    public void onSearchViewShown() {
        isSearchOpened.set(true);
    }

    @Override
    public void onSearchViewClosed() {
        isSearchOpened.set(false);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        showProgressDialog(true, "Searching recipes for " + s);
        searchRecipes(s, true);
        return false;
    }

    @Override
    public void onQueryTextChange(String s) {
        searchText.set(s);
    }

    public interface ActionListener extends KeyboardListener {
        void onStartFilter();
    }

    public abstract class SearchRecipesObserver<T> implements SingleObserver<T> {

        @Override
        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
            loadMoreProgressBarVisibility.set(View.INVISIBLE);
            subscriptions.add(d);
        }

        @Override
        public abstract void onSuccess(@io.reactivex.annotations.NonNull T t);

        @Override
        public void onError(Throwable e) {
            loadMoreProgressBarVisibility.set(View.INVISIBLE);
            showProgressDialog(false, null);
            Log.d(MainActivity.LOG_TAG, e.getMessage());
            showError(e);
        }
    }
}
