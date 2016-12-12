package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentIngredientAnalysisBinding;
import com.mlsdev.recipefinder.view.listener.OnIngredientAnalyzedListener;
import com.mlsdev.recipefinder.view.utils.ResourcesUtils;

public class IngredientAnalysisFragment extends Fragment implements OnIngredientAnalyzedListener {
    private FragmentIngredientAnalysisBinding binding;
    private IngredientAnalysisViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient_analysis, container, false);
        viewModel = new IngredientAnalysisViewModel((AppCompatActivity) getActivity(), this);
        binding.setViewModel(viewModel);

        initDiagram();
        return binding.getRoot();
    }

    private void initDiagram() {
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setExtraOffsets(5, 10, 5, 5);
        binding.pieChart.setDragDecelerationFrictionCoef(0.96f);

        binding.pieChart.setDrawHoleEnabled(true);
        binding.pieChart.setHoleColor(android.R.color.transparent);

        binding.pieChart.setTransparentCircleColor(ResourcesUtils.getColor(getActivity(), android.R.color.white));
        binding.pieChart.setTransparentCircleAlpha(50);

        binding.pieChart.setHoleRadius(40f);
        binding.pieChart.setTransparentCircleRadius(45f);

        binding.pieChart.setDrawCenterText(true);
        binding.pieChart.setCenterText("Balance");

        binding.pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        binding.pieChart.setRotationEnabled(false);
        binding.pieChart.setHighlightPerTapEnabled(true);
        binding.pieChart.setDrawEntryLabels(true);
        binding.pieChart.setEntryLabelColor(ResourcesUtils.getColor(getActivity(), android.R.color.white));
        binding.pieChart.setEntryLabelTextSize(15f);

        Legend l = binding.pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
    }

    @Override
    public void onIngredientAnalyzed(PieData diagramData) {
        binding.pieChart.setData(diagramData);
        binding.pieChart.highlightValue(null);
        binding.pieChart.invalidate();
        binding.pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }
}
