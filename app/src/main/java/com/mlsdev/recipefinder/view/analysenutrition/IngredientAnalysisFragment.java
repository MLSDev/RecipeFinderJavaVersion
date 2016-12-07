package com.mlsdev.recipefinder.view.analysenutrition;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentIngredientAnalysisBinding;

public class IngredientAnalysisFragment extends Fragment {
    private FragmentIngredientAnalysisBinding binding;
    private IngredientAnalysisViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient_analysis, container, false);
        viewModel = new IngredientAnalysisViewModel((AppCompatActivity) getActivity());
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }
}
