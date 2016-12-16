package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.PieData;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentIngredientAnalysisBinding;
import com.mlsdev.recipefinder.view.listener.OnIngredientAnalyzedListener;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;

public class IngredientAnalysisFragment extends Fragment implements OnIngredientAnalyzedListener {
    private FragmentIngredientAnalysisBinding binding;
    private IngredientAnalysisViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient_analysis, container, false);
        viewModel = new IngredientAnalysisViewModel((AppCompatActivity) getActivity(), this);
        binding.setViewModel(viewModel);
        DiagramUtils.preparePieChart(getActivity(), binding.pieChart);
        return binding.getRoot();
    }

    @Override
    public void onIngredientAnalyzed(PieData diagramData) {
        DiagramUtils.setData(binding.pieChart, diagramData);
    }
}
