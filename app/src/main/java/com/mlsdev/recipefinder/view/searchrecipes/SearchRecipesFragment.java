package com.mlsdev.recipefinder.view.searchrecipes;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentSearchRecipesBinding;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.fragment.RecipeListFragment;

public class SearchRecipesFragment extends RecipeListFragment implements RecipeListAdapter.OnLastItemShownListener {
    private FragmentSearchRecipesBinding binding;
    private SearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_recipes, container, false);

        if (viewModel == null)
            viewModel = new SearchViewModel(this, this);

        binding.setViewModel(viewModel);
        binding.etSearch.setOnEditorActionListener(new OnActionButtonClickListener());
        initRecyclerView(binding.rvRecipeList);

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
        if (requestCode == SearchViewModel.FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            viewModel.onApplyFilterOptions(data.getExtras());
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
