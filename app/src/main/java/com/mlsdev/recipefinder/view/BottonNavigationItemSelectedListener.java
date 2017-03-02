package com.mlsdev.recipefinder.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.fragments.TabFragment;

import static com.mlsdev.recipefinder.view.enums.TabItemType.ANALYSE;
import static com.mlsdev.recipefinder.view.enums.TabItemType.FAVORITES;
import static com.mlsdev.recipefinder.view.enums.TabItemType.SEARCH;

public class BottonNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private TabFragment analyseNutritionFragment;
    private TabFragment searchRecipesFragment;
    private TabFragment favoriteRecipesFragment;
    private TabFragment currentFragment;
    private int checkedItemId = -1;

    public BottonNavigationItemSelectedListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        analyseNutritionFragment = TabFragment.getNewInstance(ANALYSE);
        searchRecipesFragment = TabFragment.getNewInstance(SEARCH);
        favoriteRecipesFragment = TabFragment.getNewInstance(FAVORITES);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (checkedItemId == item.getItemId()) {
            currentFragment.scrollToTop();
            return true;
        }

        checkedItemId = item.getItemId();

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

    private void replaceFragment(TabFragment fragment) {
        currentFragment = fragment;

        clearBackStack();

        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fl_content, fragment)
                .commit();
    }

    private void clearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            clearBackStack();
        }
    }

}
