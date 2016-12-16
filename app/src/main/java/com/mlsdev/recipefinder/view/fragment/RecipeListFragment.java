package com.mlsdev.recipefinder.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.fragments.NavigationFragment;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.recipedetails.RecipeDetailsFragment;
import com.mlsdev.recipefinder.view.recipedetails.RecipeViewModel;
import com.mlsdev.recipefinder.view.searchrecipes.RecipeListAdapter;
import com.mlsdev.recipefinder.view.utils.Utils;

import java.util.List;

public class RecipeListFragment extends NavigationFragment implements RecipeListAdapter.OnItemClickListener,
        RecipeListAdapter.OnLastItemShownListener, OnRecipesLoadedListener {
    protected RecipeListAdapter recipeListAdapter;
    protected RecyclerView recipeRecyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onItemClicked(Recipe recipe) {
        Bundle recipeData = new Bundle();
        recipeData.putSerializable(RecipeViewModel.RECIPE_DATA_KEY, recipe);
        Fragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(recipeData);
        ((MainActivity) getActivity()).addFragmentToBackstack(fragment);
    }

    @Override
    public void onLastItemShown() {

    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        recipeRecyclerView = recyclerView;
        if (recipeListAdapter == null)
            recipeListAdapter = new RecipeListAdapter(this, this);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setAdapter(recipeListAdapter);
    }

    protected void initSwipeRefreshLayout(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout = refreshLayout;
        swipeRefreshLayout.setOnRefreshListener(listener);
        swipeRefreshLayout.setColorSchemeColors(
                Utils.getColor(getActivity(), R.color.colorPrimaryDark),
                Utils.getColor(getActivity(), R.color.colorPrimary),
                Utils.getColor(getActivity(), R.color.colorAccent)
        );
    }

    @Override
    public void onRecipesLoaded(List<Recipe> recipes) {
        recipeListAdapter.setData(recipes);
        stopRefreshing();
    }

    @Override
    public void onMoreRecipesLoaded(List<Recipe> moreRecipes) {
        recipeListAdapter.setMoreData(moreRecipes);
        stopRefreshing();
    }

    protected void stopRefreshing() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }
}
