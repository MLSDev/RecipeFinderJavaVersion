package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.databinding.ActivityRecipeAnalysisDetailsBinding;
import com.mlsdev.recipefinder.view.BaseActivity;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeAnalysisDetailsActivity extends BaseActivity implements HasSupportFragmentInjector {
    public static final String RECIPE_ANALYSING_RESULT_KEY = "recipe_analysing_result";
    private ActivityRecipeAnalysisDetailsBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DiagramUtils diagramUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_analysis_details);
        binding.setViewModel(ViewModelProviders.of(this, viewModelFactory).get(ViewModel.class));
        binding.getViewModel().setData(getIntent().getExtras());
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }

    public class ViewModel extends android.arch.lifecycle.ViewModel {
        public final ObservableField<String> calories = new ObservableField<>();
        public final ObservableField<String> yield = new ObservableField<>();
        public final ObservableInt chartVisibility = new ObservableInt(View.GONE);

        @Inject
        public ViewModel() {
        }

        public void setData(Bundle recipeAnalysingData) {
            NutritionAnalysisResult nutritionAnalysisResult = recipeAnalysingData
                    .getParcelable(RECIPE_ANALYSING_RESULT_KEY);

            showResults(nutritionAnalysisResult);
        }

        private void showResults(NutritionAnalysisResult nutritionAnalysisResult) {
            if (nutritionAnalysisResult == null)
                return;

            calories.set(getString(R.string.calories, nutritionAnalysisResult.getCalories()));
            yield.set(getString(R.string.yields, String.valueOf(nutritionAnalysisResult.getYield())));

            List<PieEntry> pieEntries = diagramUtils.preparePieEntries(nutritionAnalysisResult.getTotalNutrients());
            chartVisibility.set(pieEntries.isEmpty() ? View.GONE : View.VISIBLE);

            if (pieEntries.isEmpty())
                return;

            PieDataSet pieDataSet = diagramUtils.createPieDataSet(pieEntries, "Nutrients", null);
            PieData pieData = diagramUtils.createPieData(pieDataSet);

            diagramUtils.setData(binding.pieChart, pieData);
        }
    }
}
