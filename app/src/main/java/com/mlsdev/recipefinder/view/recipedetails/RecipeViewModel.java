package com.mlsdev.recipefinder.view.recipedetails;

import android.databinding.ObservableField;
import android.os.Bundle;

import com.mlsdev.recipefinder.data.entity.Recipe;

import java.util.List;

public class RecipeViewModel {
    public static final String RECIPE_DATA_KEY = "recipe_data_key";
    private Recipe recipe;
    public ObservableField<String> recipeTitle;
    public ObservableField<String> recipeImageUrl;
    public ObservableField<String> recipeHealthLabels;
    public ObservableField<String> recipeDietLabels;
    public ObservableField<String> recipeIngredients;

    public RecipeViewModel(Bundle recipeData) {
        recipeTitle = new ObservableField<>();
        recipeImageUrl = new ObservableField<>();
        recipeHealthLabels = new ObservableField<>();
        recipeDietLabels = new ObservableField<>();
        recipeIngredients = new ObservableField<>();

        if (recipeData != null && recipeData.containsKey(RECIPE_DATA_KEY)) {
            recipe = recipeData.getParcelable(RECIPE_DATA_KEY);
            if (recipe != null) {
                recipeTitle.set(recipe.getLabel());
                recipeImageUrl.set(recipe.getImage());

                recipeHealthLabels.set(getLabelsAsString(recipe.getHealthLabels()));
                recipeDietLabels.set(getLabelsAsString(recipe.getDietLabels()));
                recipeIngredients.set(getIngredientsAsString(recipe.getIngredientLines()));
            }
        }
    }

    private String getIngredientsAsString(List<String> ingredients) {
        String ingredientsAsString = "";

        for (String ingredient : ingredients) {
            if (!ingredientsAsString.isEmpty())
                ingredientsAsString = ingredientsAsString.concat(",\n");

            ingredientsAsString = ingredientsAsString.concat(ingredient);
        }

        return ingredientsAsString;
    }

    private String getLabelsAsString(List<String> labels) {
        String labelsAsString = "";

        for (String label : labels) {
            if (!labelsAsString.isEmpty())
                labelsAsString = labelsAsString.concat(", ");

            labelsAsString = labelsAsString.concat(label);
        }

        return labelsAsString;
    }
}
