package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityRecipeAnalysisDetailsBinding;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;

public class RecipeAnalysisDetailsActivity extends AppCompatActivity {
    private ActivityRecipeAnalysisDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_analysis_details);
        DiagramUtils.preparePieChrt(this, binding.pieChart);
    }

    public class ViewModel {
        public final ObservableField<String> title;
        public final ObservableField<String> preparation;
        public final ObservableField<String> yield;
        public final ObservableField<String> ingredients;

        public ViewModel() {
            title = new ObservableField<>();
            preparation = new ObservableField<>();
            yield = new ObservableField<>();
            ingredients = new ObservableField<>();
        }
    }
}
