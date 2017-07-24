package com.mlsdev.recipefinder.view.recipedetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentRecipeDetailsBinding;
import com.mlsdev.recipefinder.di.Injectable;
import com.mlsdev.recipefinder.view.Extras;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.fragment.BaseFragment;

import javax.inject.Inject;

public class RecipeDetailsFragment extends BaseFragment implements Injectable {
    private FragmentRecipeDetailsBinding binding;
    private RecipeViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeViewModel.class);
        viewModel.setRecipeData(getArguments());
        viewModel.setActionListener(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);
        binding.setViewModel(viewModel);

        ((MainActivity) getActivity()).setSupportActionBar(binding.toolbar);
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String imageTransitionName = getArguments().getString(Extras.IMAGE_TRANSITION_NAME);

        if (imageTransitionName != null)
            ViewCompat.setTransitionName(binding.ivRecipeImage, imageTransitionName);

        Bitmap bitmap = getArguments().getParcelable(Extras.IMAGE_DATA);
        if (bitmap != null)
            binding.ivRecipeImage.setImageBitmap(bitmap);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
    }
}
