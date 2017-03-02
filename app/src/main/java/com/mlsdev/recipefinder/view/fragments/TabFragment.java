package com.mlsdev.recipefinder.view.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mlsdev.recipefinder.view.analysenutrition.AnalyseNutritionFragment;
import com.mlsdev.recipefinder.view.enums.TabItemType;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoriteRecipesFragment;
import com.mlsdev.recipefinder.view.searchrecipes.SearchRecipesFragment;

public abstract class TabFragment extends Fragment {

    @Nullable
    public static TabFragment getNewInstance(TabItemType tabItemType) {

        switch (tabItemType) {
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

    /**
     * Scrolls the root view of the fragment to top by clicking on a current tab in the navigation view.
     */
    public void scrollToTop() {

    }

}
