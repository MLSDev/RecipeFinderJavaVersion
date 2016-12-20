package com.mlsdev.recipefinder.view.recipedetails;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.FragmentRecipeDetailsBinding;
import com.mlsdev.recipefinder.view.Extras;
import com.mlsdev.recipefinder.view.MainActivity;

public class RecipeDetailsFragment extends Fragment {
    private FragmentRecipeDetailsBinding binding;
    private RecipeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new RecipeViewModel(getActivity(), getArguments());
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
