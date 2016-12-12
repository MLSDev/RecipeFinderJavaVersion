package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentRecipeAnalysisBinding;

public class RecipeAnalysisFragment extends Fragment implements RecipeAnalysisViewModel.OnAddIngredientListener {
    private FragmentRecipeAnalysisBinding binding;
    private RecipeAnalysisViewModel viewModel;
    private IngredientsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_analysis, container, false);
        viewModel = new RecipeAnalysisViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new IngredientsAdapter();
        binding.rvIngredients.setHasFixedSize(true);
        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvIngredients.setAdapter(adapter);
    }

    @Override
    public void onAddIngredient(String text) {
        adapter.addItem(text);
    }
}
