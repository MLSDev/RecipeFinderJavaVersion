package com.mlsdev.recipefinder.view.searchrecipes;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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

public class SearchViewModel extends BaseViewModel {
    public final ObservableInt loadMoreProgressBarVisibility = new ObservableInt(View.INVISIBLE);
    public final ObservableInt searchLabelVisibility = new ObservableInt(View.VISIBLE);
    public final ObservableInt filterButtonVisibility = new ObservableInt(View.INVISIBLE);
    public final ObservableField<String> searchText = new ObservableField<>();
    public final ObservableField<String> searchLabelText;
    private OnRecipesLoadedListener onRecipesLoadedListener;
    private Map<String, String> searchParams;
    private ActionListener actionListener;

    public SearchViewModel(@NonNull Context context, @NonNull ActionListener actionListener,
                           @NonNull OnRecipesLoadedListener onRecipesLoadedListener) {
        super(context);
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        this.actionListener = actionListener;
        searchLabelText = new ObservableField<>(context.getString(R.string.label_search));
        searchParams = new ArrayMap<>();
        keyboardListener = actionListener;
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

        String prevSearchText = searchParams.containsKey(ParameterKeys.QUERY) ? searchParams.get(ParameterKeys.QUERY) : "";

        if (forceUpdate || !(prevSearchText.equals(searchText.toLowerCase())))
            repository.setCacheIsDirty();

        searchParams.put(ParameterKeys.QUERY, searchText);
        subscriptions.clear();

        showProgressDialog(!forceUpdate, "Searching...");
        searchLabelVisibility.set(View.INVISIBLE);

        repository.searchRecipes(searchParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SearchRecipesObserver<List<Recipe>>() {
                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        String commonSearchLabelText = context.getString(R.string.label_search);
                        String nothingFoundText = context.getString(R.string.label_search_nothing_found);
                        searchLabelText.set(recipes.isEmpty() ? nothingFoundText : commonSearchLabelText);
                        searchLabelVisibility.set(recipes.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        filterButtonVisibility.set(recipes.isEmpty() ? View.INVISIBLE : View.VISIBLE);
                        onRecipesLoadedListener.onRecipesLoaded(recipes);
                    }
                });
    }

    public void loadMoreRecipes() {
        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.QUERY, this.searchText.get().toLowerCase());
        subscriptions.clear();
        loadMoreProgressBarVisibility.set(View.VISIBLE);

        repository.loadMore(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SearchRecipesObserver<List<Recipe>>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull List<Recipe> recipes) {
                        onRecipesLoadedListener.onMoreRecipesLoaded(recipes);
                    }
                });

    }

    public void refresh() {
        searchRecipes(searchText.get(), true);
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

        searchRecipes(this.searchText.get().toLowerCase(), true);
    }

    /**
     * Handles the {@link android.widget.Button} clicking on.
     * This method is added into the {@link android.widget.Button}'s attribute list in the layout.
     */
    public void onFilterClick(View view) {
        actionListener.onStartFilter();
    }

    /**
     * Handles the {@link android.widget.EditText}'s text changing.
     * This method is added into the {@link android.widget.EditText}'s attribute list in the layout.
     */
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        searchText.set(text.toString());
    }

    /**
     * Handles the keyboard action button clicking on.
     * This method is added into the {@link android.widget.EditText}'s attribute list in the layout.
     */
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            searchRecipes(textView.getText().toString(), false);
            keyboardListener.onHideKeyboard();
            return true;
        }

        return false;
    }

    public interface ActionListener extends KeyboardListener {
        void onStartFilter();
    }

    public abstract class SearchRecipesObserver<T> implements SingleObserver<T> {

        @Override
        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
            loadMoreProgressBarVisibility.set(View.INVISIBLE);
            showProgressDialog(false, null);
            subscriptions.add(d);
        }

        @Override
        public abstract void onSuccess(@io.reactivex.annotations.NonNull T t);

        @Override
        public void onError(Throwable e) {
            loadMoreProgressBarVisibility.set(View.INVISIBLE);
            showProgressDialog(false, null);
            Log.d(MainActivity.LOG_TAG, e.getMessage());
        }
    }
}
