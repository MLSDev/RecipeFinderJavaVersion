package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.view.listener.OnDataLoadedListener;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class RecipeAnalysisDetailsViewModel extends BaseViewModel implements LifecycleObserver {

    public final ObservableField<String> calories = new ObservableField<>();
    public final ObservableField<String> yield = new ObservableField<>();
    public final ObservableInt chartVisibility = new ObservableInt(View.GONE);
    private DiagramUtils diagramUtils;
    private PieData pieData;
    private OnDataLoadedListener<PieData> onDataLoadedListener;

    @Inject
    public RecipeAnalysisDetailsViewModel(DiagramUtils diagramUtils) {
        this.diagramUtils = diagramUtils;
    }

    public void setOnDataLoadedListener(OnDataLoadedListener<PieData> onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void start() {
        if (onDataLoadedListener != null)
            onDataLoadedListener.onDataLoaded(pieData);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Log.d("RF", "lifecycle stop");
    }

    public void setData(Bundle recipeAnalysingData) {
        NutritionAnalysisResult nutritionAnalysisResult = recipeAnalysingData
                .getParcelable(RecipeAnalysisDetailsActivity.RECIPE_ANALYSING_RESULT_KEY);

        showResults(nutritionAnalysisResult);
    }

    private void showResults(NutritionAnalysisResult nutritionAnalysisResult) {
        if (nutritionAnalysisResult == null)
            return;

        calories.set(context.getString(R.string.calories, nutritionAnalysisResult.getCalories()));
        yield.set(context.getString(R.string.yields, String.valueOf(nutritionAnalysisResult.getYield())));

        List<PieEntry> pieEntries = diagramUtils.preparePieEntries(nutritionAnalysisResult.getTotalNutrients());
        chartVisibility.set(pieEntries.isEmpty() ? View.GONE : View.VISIBLE);

        if (pieEntries.isEmpty())
            return;

        PieDataSet pieDataSet = diagramUtils.createPieDataSet(pieEntries, "Nutrients", null);
        pieData = diagramUtils.createPieData(pieDataSet);

        if (onDataLoadedListener != null)
            onDataLoadedListener.onDataLoaded(pieData);
    }

}
