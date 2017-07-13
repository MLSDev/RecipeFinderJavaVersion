package com.mlsdev.recipefinder.view.favoriterecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentFavoriteRecipesBinding;
import com.mlsdev.recipefinder.view.fragment.RecipeListFragment;

import static com.mlsdev.recipefinder.view.searchrecipes.RecipeListAdapter.OnLastItemShownListener;

public class FavoriteRecipesFragment extends RecipeListFragment implements OnLastItemShownListener {
    private FragmentFavoriteRecipesBinding binding;
    private FavoritesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (binding == null || viewModel == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_recipes, container, false);
            viewModel = new FavoritesViewModel(getActivity(), this);
            binding.setViewModel(viewModel);
        }

        initRecyclerView(binding.rvRecipeList);
        return binding.getRoot();
    }

    @Override
    public void scrollToTop() {
        binding.rvRecipeList.smoothScrollToPosition(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getFavoriteRecipes();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.onStop();
    }
}
