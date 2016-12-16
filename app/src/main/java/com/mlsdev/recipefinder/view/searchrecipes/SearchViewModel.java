package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {
    public static final int FILTER_REQUEST_CODE = 0;
    private Fragment fragment;
    public final ObservableInt loadMoreProgressBarVisibility;
    public final ObservableInt searchLabelVisibility;
    public final ObservableInt filterButtonVisibility;
    public final ObservableField<String> searchText;
    public final ObservableField<String> searchLabelText;
    private OnRecipesLoadedListener onRecipesLoadedListener;
    private Map<String, String> searchParams;
    private DialogFragment filterFragment;
    private boolean moreRecipes = true;

    public SearchViewModel(@NonNull Fragment fragment, @NonNull OnRecipesLoadedListener onRecipesLoadedListener) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.onRecipesLoadedListener = onRecipesLoadedListener;
        loadMoreProgressBarVisibility = new ObservableInt(View.INVISIBLE);
        searchLabelVisibility = new ObservableInt(View.VISIBLE);
        filterButtonVisibility = new ObservableInt(View.INVISIBLE);
        searchText = new ObservableField<>();
        searchLabelText = new ObservableField<>(fragment.getString(R.string.label_search));
        searchParams = new ArrayMap<>();
        filterFragment = new FilterDialogFragment();
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

        Subscription subscription = repository.searchRecipes(searchParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SearchRecipesObserver<List<Recipe>>() {
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
        if (!moreRecipes)
            return;

        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.QUERY, this.searchText.get().toLowerCase());
        subscriptions.clear();
        loadMoreProgressBarVisibility.set(View.VISIBLE);

        Subscription subscription = repository.loadMore(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SearchRecipesObserver<List<Recipe>>() {
                    @Override
                    public void onNext(List<Recipe> recipes) {
                        moreRecipes = !recipes.isEmpty();
                        onRecipesLoadedListener.onMoreRecipesLoaded(recipes);
                    }
                });

        subscriptions.add(subscription);

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
        filterFragment.setTargetFragment(fragment, FILTER_REQUEST_CODE);
        filterFragment.show(fragment.getActivity().getSupportFragmentManager(), "Filter");
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
            ((MainActivity) fragment.getActivity()).hideSoftKeyboard();
            return true;
        }

        return false;
    }

    public abstract class SearchRecipesObserver<T> implements Observer<T> {

        @Override
        public void onCompleted() {
            loadMoreProgressBarVisibility.set(View.INVISIBLE);
            showProgressDialog(false, null);
        }

        @Override
        public void onError(Throwable e) {
            loadMoreProgressBarVisibility.set(View.INVISIBLE);
            showProgressDialog(false, null);
            Log.d(MainActivity.LOG_TAG, e.getMessage());
        }

        @Override
        public abstract void onNext(T t);
    }
}
