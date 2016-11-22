package com.mlsdev.recipefinder.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.enums.NavigationItem;
import com.mlsdev.recipefinder.view.fragments.NavigationFragment;

public class NavigationManager implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private NavigationFragment analyseNutritionFragment;
    private NavigationFragment searchRecipesFragment;
    private NavigationFragment favoriteRecipesFragment;

    public NavigationManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        analyseNutritionFragment = NavigationFragment.getNewInstance(NavigationItem.ANALYSE);
        searchRecipesFragment = NavigationFragment.getNewInstance(NavigationItem.SEARCH);
        favoriteRecipesFragment = NavigationFragment.getNewInstance(NavigationItem.FAVORITES);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_analyse_nutrition:
                replaceFragment(analyseNutritionFragment);
                break;
            case R.id.action_search_recipe:
                replaceFragment(searchRecipesFragment);
                break;
            case R.id.action_favorites:
                replaceFragment(favoriteRecipesFragment);
                break;
        }

        return false;
    }

    private void replaceFragment(NavigationFragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }
}
