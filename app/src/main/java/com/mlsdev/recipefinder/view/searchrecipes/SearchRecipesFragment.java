package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.databinding.FragmentSearchRecipesBinding;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.fragments.NavigationFragment;

import java.util.List;

public class SearchRecipesFragment extends NavigationFragment implements SearchViewModel.OnRecipesLoadedListener, RecipeListAdapter.OnLastItemShownListener {
    private FragmentSearchRecipesBinding binding;
    private SearchViewModel viewModel;
    private RecipeListAdapter recipeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (viewModel == null)
            viewModel = new SearchViewModel(getActivity().getApplicationContext(), this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_recipes, container, false);
        binding.setViewModel(viewModel);
        binding.etSearch.setOnEditorActionListener(new OnActionButtonClickListener());
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        if (recipeListAdapter == null)
            recipeListAdapter = new RecipeListAdapter(this);
        binding.rvRecipeList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvRecipeList.setHasFixedSize(true);
        binding.rvRecipeList.setAdapter(recipeListAdapter);
    }

    @Override
    public void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRecipesLoaded(List<Recipe> recipes) {
        recipeListAdapter.setData(recipes);
    }

    @Override
    public void onLastItemShown() {
        // TODO: 11/25/16 load more recipes
    }

    public class OnActionButtonClickListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

            if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchRecipes(binding.etSearch.getText().toString(), true);
                ((MainActivity) getActivity()).hideSoftKeyboard();
                return true;
            }

            return false;
        }

    }
}
