package com.mlsdev.recipefinder.view.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.support.annotation.Nullable;

import com.mlsdev.recipefinder.view.analysenutrition.AnalyseNutritionFragment;
import com.mlsdev.recipefinder.view.enums.TabItemType;
import com.mlsdev.recipefinder.view.favoriterecipes.FavoriteRecipesFragment;
import com.mlsdev.recipefinder.view.searchrecipes.SearchRecipeFragment;

public abstract class TabFragment extends LifecycleFragment {

    @Nullable
    public static TabFragment getNewInstance(TabItemType tabItemType) {

        switch (tabItemType) {
            case ANALYSE:
                return new AnalyseNutritionFragment();
            case SEARCH:
                return new SearchRecipeFragment();
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
