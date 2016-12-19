package com.mlsdev.recipefinder.view.recipedetails;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityRecipeDetailsBinding;
import com.mlsdev.recipefinder.view.BaseActivity;

public class RecipeDetailsActivity extends BaseActivity {
    private ActivityRecipeDetailsBinding binding;
    private RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);
        Bundle data = getIntent().getExtras();
        viewModel = new RecipeViewModel(this, data);
        Bitmap bitmap = data.getParcelable("image");
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if (bitmap != null)
            binding.ivRecipeImage.setImageBitmap(bitmap);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
    }
}
