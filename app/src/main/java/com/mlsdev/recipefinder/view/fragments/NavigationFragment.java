package com.mlsdev.recipefinder.view.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mlsdev.recipefinder.view.analysenutrition.AnalyseNutritionFragment;
import com.mlsdev.recipefinder.view.enums.NavigationItem;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoriteRecipesFragment;
import com.mlsdev.recipefinder.view.searchrecipes.SearchRecipesFragment;

public abstract class NavigationFragment extends Fragment {

    @Nullable
    public static NavigationFragment getNewInstance(NavigationItem navigationItem) {

        switch (navigationItem) {
            case ANALYSE:
                return new AnalyseNutritionFragment();
            case SEARCH:
                return new SearchRecipesFragment();
            case FAVORITES:
                return new FavoriteRecipesFragment();
            default:
                return null;
        }

    }

}
