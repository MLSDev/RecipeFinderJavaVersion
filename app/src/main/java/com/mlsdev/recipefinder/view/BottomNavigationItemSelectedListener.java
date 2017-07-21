package com.mlsdev.recipefinder.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.fragments.TabFragment;

import javax.inject.Inject;

import static android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import static com.mlsdev.recipefinder.view.enums.TabItemType.ANALYSE;
import static com.mlsdev.recipefinder.view.enums.TabItemType.FAVORITES;
import static com.mlsdev.recipefinder.view.enums.TabItemType.SEARCH;

public class BottomNavigationItemSelectedListener implements OnNavigationItemSelectedListener,
        LifecycleObserver {
    private FragmentManager fragmentManager;
    private TabFragment analyseNutritionFragment;
    private TabFragment searchRecipesFragment;
    private TabFragment favoriteRecipesFragment;
    private TabFragment currentFragment;
    private int checkedItemId = -1;
    private MenuItem currentMenuItem;

    @Inject
    public BottomNavigationItemSelectedListener() {
        analyseNutritionFragment = TabFragment.getNewInstance(ANALYSE);
        searchRecipesFragment = TabFragment.getNewInstance(SEARCH);
        favoriteRecipesFragment = TabFragment.getNewInstance(FAVORITES);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setCurrentMenuItem(MenuItem currentMenuItem) {
        if (this.currentMenuItem == null)
            this.currentMenuItem = currentMenuItem;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (currentMenuItem != null)
            onNavigationItemSelected(currentMenuItem);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        currentMenuItem = item;

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
