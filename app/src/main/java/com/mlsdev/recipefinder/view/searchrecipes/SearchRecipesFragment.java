package com.mlsdev.recipefinder.view.searchrecipes;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.databinding.FragmentSearchRecipesBinding;
import com.mlsdev.recipefinder.view.BaseActivity;
import com.mlsdev.recipefinder.view.fragment.RecipeListFragment;
import com.mlsdev.recipefinder.view.viewmodel.ViewModelFactory;

import java.util.List;

public class SearchRecipesFragment extends RecipeListFragment implements RecipeListAdapter.OnLastItemShownListener,
        SwipeRefreshLayout.OnRefreshListener, SearchViewModel.ActionListener, LifecycleOwner {
    public static final int FILTER_REQUEST_CODE = 0;
    private FragmentSearchRecipesBinding binding;
    private SearchViewModel viewModel;
    private MenuItem filterMenuItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_recipes, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);

        viewModelFactory = new ViewModelFactory(getActivity());

        if (viewModel == null)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

        getLifecycle().addObserver(viewModel);
        viewModel.setActionListener(this);
        viewModel.setOnRecipesLoadedListener(this);

        binding.setViewModel(viewModel);
        swipeRefreshLayout = binding.swipeToRefreshView;

        binding.searchView.setOnSearchViewListener(viewModel);

        initRecyclerView(binding.rvRecipeList);
        initSwipeRefreshLayout(binding.swipeToRefreshView, this);

        return binding.getRoot();
    }

    @Override
    public void scrollToTop() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null)
            behavior.onNestedFling(binding.clSearchRecipes, binding.appbar, null, 0, -1000, true);

        binding.rvRecipeList.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLastItemShown() {
        viewModel.loadMoreRecipes();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            viewModel.onApplyFilterOptions(data.getExtras());
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }

    @Override
    public void onStartFilter() {
        DialogFragment filterDialogFragment = new FilterDialogFragment();
        filterDialogFragment.setTargetFragment(this, FILTER_REQUEST_CODE);
        filterDialogFragment.show(getFragmentManager(), "Filter");
    }

    @Override
    public void onHideKeyboard() {
        ((BaseActivity) getActivity()).hideSoftKeyboard();
    }

    @Override
    public void onRecipesLoaded(List<Recipe> recipes) {
        super.onRecipesLoaded(recipes);
        filterMenuItem.setVisible(!recipes.isEmpty());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        filterMenuItem = menu.findItem(R.id.action_filter);
        filterMenuItem.setVisible(recipeListAdapter.getItemCount() > 0);
        MenuItem item = menu.findItem(R.id.action_search);
        binding.searchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filter:
                viewModel.onFilterClick(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
