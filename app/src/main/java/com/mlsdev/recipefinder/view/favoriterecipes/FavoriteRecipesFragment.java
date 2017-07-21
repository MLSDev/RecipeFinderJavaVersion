package com.mlsdev.recipefinder.view.favoriterecipes;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentFavoriteRecipesBinding;
import com.mlsdev.recipefinder.di.Injectable;
import com.mlsdev.recipefinder.view.fragment.RecipeListFragment;

import javax.inject.Inject;

import static com.mlsdev.recipefinder.view.searchrecipes.RecipeListAdapter.OnLastItemShownListener;

public class FavoriteRecipesFragment extends RecipeListFragment implements OnLastItemShownListener,
        Injectable {
    private FragmentFavoriteRecipesBinding binding;
    private FavoritesViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_recipes, container, false);
        initRecyclerView(binding.rvRecipeList);

        if (viewModel == null)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);

        getLifecycle().addObserver(viewModel);
        viewModel.setOnDataLoadedListener(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void scrollToTop() {
        binding.rvRecipeList.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
