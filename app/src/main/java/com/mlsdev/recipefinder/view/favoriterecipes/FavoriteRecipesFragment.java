package com.mlsdev.recipefinder.view.favoriterecipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentFavoriteRecipesBinding;
import com.mlsdev.recipefinder.view.fragments.NavigationFragment;

public class FavoriteRecipesFragment extends NavigationFragment {
    private FragmentFavoriteRecipesBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_recipes, container, false);

        return binding.getRoot();
    }
}
