package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.databinding.ActivityRecipeAnalysisDetailsBinding;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;

import java.util.List;

public class RecipeAnalysisDetailsActivity extends AppCompatActivity {
    public static final String RECIPE_ANALYSING_RESULT_KEY = "recipe_analysing_result";
    private ActivityRecipeAnalysisDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_analysis_details);
        binding.setViewModel(new ViewModel(getIntent().getExtras()));
        DiagramUtils.preparePieChart(this, binding.pieChart);

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

    public class ViewModel {
        public final ObservableField<String> calories;
        public final ObservableField<String> yield;
        public final ObservableInt chartVisibility;

        public ViewModel(Bundle recipeAnalysingData) {
            calories = new ObservableField<>();
            yield = new ObservableField<>();
            chartVisibility = new ObservableInt(View.GONE);

            NutritionAnalysisResult nutritionAnalysisResult = (NutritionAnalysisResult) recipeAnalysingData
                    .getParcelable(RECIPE_ANALYSING_RESULT_KEY);

            showResults(nutritionAnalysisResult);
        }

        private void showResults(NutritionAnalysisResult nutritionAnalysisResult) {
            if (nutritionAnalysisResult == null)
                return;

            calories.set(getString(R.string.calories, nutritionAnalysisResult.getCalories()));
            yield.set(getString(R.string.yields, String.valueOf(nutritionAnalysisResult.getYield())));

            List<PieEntry> pieEntries = DiagramUtils.preparePieEntries(nutritionAnalysisResult.getTotalNutrients());
            chartVisibility.set(pieEntries.isEmpty() ? View.GONE : View.VISIBLE);

            if (pieEntries.isEmpty())
                return;

            PieDataSet pieDataSet = DiagramUtils.createPieDataSet(RecipeAnalysisDetailsActivity.this, pieEntries, "Nutrients", null);
            PieData pieData = DiagramUtils.createPieData(RecipeAnalysisDetailsActivity.this, pieDataSet);

            DiagramUtils.setData(binding.pieChart, pieData);
        }
    }
}
