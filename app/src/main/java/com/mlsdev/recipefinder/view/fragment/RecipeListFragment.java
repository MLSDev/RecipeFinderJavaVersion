package com.mlsdev.recipefinder.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.fragments.NavigationFragment;
import com.mlsdev.recipefinder.view.listener.OnRecipesLoadedListener;
import com.mlsdev.recipefinder.view.recipedetails.RecipeDetailsFragment;
import com.mlsdev.recipefinder.view.recipedetails.RecipeViewModel;
import com.mlsdev.recipefinder.view.searchrecipes.RecipeListAdapter;

import java.util.List;

public class RecipeListFragment extends NavigationFragment implements RecipeListAdapter.OnItemClickListener,
        RecipeListAdapter.OnLastItemShownListener, OnRecipesLoadedListener {
    protected RecipeListAdapter recipeListAdapter;
    protected RecyclerView recipeRecyclerView;


    @Override
    public void onItemClicked(Recipe recipe) {
        Bundle recipeData = new Bundle();
        recipeData.putParcelable(RecipeViewModel.RECIPE_DATA_KEY, recipe);
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

    @Override
    public void onRecipesLoaded(List<Recipe> recipes) {
        recipeListAdapter.setData(recipes);
    }

    @Override
    public void onMoreRecipesLoaded(List<Recipe> moreRecipes) {
        recipeListAdapter.setMoreData(moreRecipes);
    }
}
