package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.PieData;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentIngredientAnalysisBinding;
import com.mlsdev.recipefinder.di.Injectable;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.OnKeyboardStateChangedListener;
import com.mlsdev.recipefinder.view.listener.OnIngredientAnalyzedListener;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;

import javax.inject.Inject;

public class IngredientAnalysisFragment extends LifecycleFragment implements OnIngredientAnalyzedListener,
        OnKeyboardStateChangedListener, Injectable {
    private FragmentIngredientAnalysisBinding binding;
    private IngredientAnalysisViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DiagramUtils diagramUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient_analysis, container, false);

        if (viewModel == null)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(IngredientAnalysisViewModel.class);

        getLifecycle().addObserver(viewModel);
        viewModel.setKeyboardListener(this);
        viewModel.setOnIngredientAnalyzedListener(this);

        binding.setViewModel(viewModel);
        diagramUtils.preparePieChart(binding.pieChart);
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.etIngredientInput.clearFocus();
    }

    @Override
    public void onIngredientAnalyzed(PieData diagramData) {
        diagramUtils.setData(binding.pieChart, diagramData);
    }

    @Override
    public void showKeyboard() {
        ((MainActivity) getActivity()).showSoftKeyboard();
    }

    @Override
    public void hideKeyboard() {
        ((MainActivity) getActivity()).hideSoftKeyboard();
    }
}
