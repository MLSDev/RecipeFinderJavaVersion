package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentRecipeAnalysisBinding;
import com.mlsdev.recipefinder.view.listener.OnDataLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.ViewModelFactory;

import java.util.List;

public class RecipeAnalysisFragment extends LifecycleFragment implements OnAddIngredientClickListener,
        OnDataLoadedListener<List<String>> {
    private FragmentRecipeAnalysisBinding binding;
    private RecipeAnalysisViewModel viewModel;
    private IngredientsAdapter adapter;
    private ViewModelFactory viewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModelFactory = new ViewModelFactory(getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_analysis, container, false);

        if (viewModel == null)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeAnalysisViewModel.class);

        viewModel.setAddIngredientListener(this);
        viewModel.setDataLoadedListener(this);

        getLifecycle().addObserver(viewModel);

        binding.setViewModel(viewModel);
        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new IngredientsAdapter(this);
        binding.rvIngredients.setHasFixedSize(true);
        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvIngredients.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RecipeAnalysisViewModel.ADD_INGREDIENT_REQUEST_CODE
                && resultCode == Activity.RESULT_OK && data != null) {
            adapter.addItem(data);
            binding.rvIngredients.smoothScrollToPosition(adapter.getItemCount());
            viewModel.setIngredients(adapter.getIngredientList());
        }

    }

    @Override
    public void onAddIngredientButtonClick() {
        AddIngredientDialogFragment dialogFragment = new AddIngredientDialogFragment();
        dialogFragment.setTargetFragment(this, RecipeAnalysisViewModel.ADD_INGREDIENT_REQUEST_CODE);
        dialogFragment.show(getFragmentManager(), "add_ingredient");
    }

    @Override
    public void onDataLoaded(List<String> ingredients) {
        adapter.setData(ingredients);
    }

    @Override
    public void onMoreDataLoaded(List<String> moreData) {

    }
}
