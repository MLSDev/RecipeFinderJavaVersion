package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.github.mikephil.charting.data.PieData;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityRecipeAnalysisDetailsBinding;
import com.mlsdev.recipefinder.view.BaseActivity;
import com.mlsdev.recipefinder.view.listener.OnDataLoadedListener;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeAnalysisDetailsActivity extends BaseActivity implements HasSupportFragmentInjector, LifecycleRegistryOwner, OnDataLoadedListener<PieData> {
    public static final String RECIPE_ANALYSING_RESULT_KEY = "recipe_analysing_result";
    private ActivityRecipeAnalysisDetailsBinding binding;
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private RecipeAnalysisDetailsViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DiagramUtils diagramUtils;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_analysis_details);

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeAnalysisDetailsViewModel.class);
            viewModel.setData(getIntent().getExtras());
        }

        getLifecycle().addObserver(viewModel);
        viewModel.setOnDataLoadedListener(this);
        binding.setViewModel(viewModel);
        diagramUtils.preparePieChart(binding.pieChart);

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onDataLoaded(PieData pieData) {
        diagramUtils.setData(binding.pieChart, pieData);
    }

    @Override
    public void onMoreDataLoaded(PieData moreRecipes) {
    }

}
