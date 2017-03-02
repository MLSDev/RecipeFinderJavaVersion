package com.mlsdev.recipefinder.view.analysenutrition;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentAnalyseNutritionBinding;
import com.mlsdev.recipefinder.view.analysenutrition.ingredient.IngredientAnalysisFragment;
import com.mlsdev.recipefinder.view.analysenutrition.recipe.RecipeAnalysisFragment;
import com.mlsdev.recipefinder.view.fragments.TabFragment;

public class AnalyseNutritionFragment extends TabFragment {
    public static final int INGREDIENT_ANALYSIS_FRAGMENT = 0;
    public static final int RECIPE_ANALYSIS_FRAGMENT = 1;
    public static final int PAGES_NUMBER = 2;
    private FragmentAnalyseNutritionBinding binding;
    private ViewPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_analyse_nutrition, container, false);
        initViewPager();
        return binding.getRoot();
    }

    private void initViewPager() {
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        binding.vpAnalysisPages.setAdapter(pagerAdapter);
        binding.vpAnalysisPages.setOffscreenPageLimit(PAGES_NUMBER);
        binding.tlAnalysisTabs.setupWithViewPager(binding.vpAnalysisPages);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        Fragment nutritionAnalysisFragment;
        Fragment recipeAnalysisFragment;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            nutritionAnalysisFragment = new IngredientAnalysisFragment();
            recipeAnalysisFragment = new RecipeAnalysisFragment();
        }

        @Override
        public Fragment getItem(int position) {
            return position == RECIPE_ANALYSIS_FRAGMENT
                    ? recipeAnalysisFragment
                    : nutritionAnalysisFragment;
        }

        @Override
        public int getCount() {
            return PAGES_NUMBER;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(position == RECIPE_ANALYSIS_FRAGMENT
                    ? R.string.tab_recipe_analysis
                    : R.string.tab_nutrition_analysis);
        }
    }
}
