package com.mlsdev.recipefinder.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.fragments.NavigationFragment;

import static com.mlsdev.recipefinder.view.enums.NavigationItem.ANALYSE;
import static com.mlsdev.recipefinder.view.enums.NavigationItem.FAVORITES;
import static com.mlsdev.recipefinder.view.enums.NavigationItem.SEARCH;

public class NavigationManager implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private NavigationFragment analyseNutritionFragment;
    private NavigationFragment searchRecipesFragment;
    private NavigationFragment favoriteRecipesFragment;

    public NavigationManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        analyseNutritionFragment = NavigationFragment.getNewInstance(ANALYSE);
        searchRecipesFragment = NavigationFragment.getNewInstance(SEARCH);
        favoriteRecipesFragment = NavigationFragment.getNewInstance(FAVORITES);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_analyse_nutrition:
                replaceFragment(analyseNutritionFragment);
                return true;
            case R.id.action_search_recipe:
                replaceFragment(searchRecipesFragment);
                return true;
            case R.id.action_favorites:
                replaceFragment(favoriteRecipesFragment);
                return true;
            default:
                return false;
        }

    }

    private void replaceFragment(NavigationFragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
    }

}
