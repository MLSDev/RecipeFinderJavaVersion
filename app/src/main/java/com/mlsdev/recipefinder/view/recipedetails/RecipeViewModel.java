package com.mlsdev.recipefinder.view.recipedetails;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.Collection;
import java.util.List;

public class RecipeViewModel extends BaseViewModel {
    public static final String RECIPE_DATA_KEY = "recipe_data_key";
    private Recipe recipe;
    public ObservableField<String> recipeTitle;
    public ObservableField<String> recipeImageUrl;
    public ObservableField<String> recipeHealthLabels;
    public ObservableField<String> recipeDietLabels;
    public ObservableField<String> recipeIngredients;
    public ObservableBoolean favoriteImageStateChecked;

    public RecipeViewModel(Context context, Bundle recipeData) {
        super(context);
        recipeTitle = new ObservableField<>();
        recipeImageUrl = new ObservableField<>();
        recipeHealthLabels = new ObservableField<>();
        recipeDietLabels = new ObservableField<>();
        recipeIngredients = new ObservableField<>();
        favoriteImageStateChecked = new ObservableBoolean(false);

        if (recipeData != null && recipeData.containsKey(RECIPE_DATA_KEY)) {
            recipe = (Recipe) recipeData.getSerializable(RECIPE_DATA_KEY);
            if (recipe != null) {
                recipeTitle.set(recipe.getLabel());
                recipeImageUrl.set(recipe.getImage());

                recipeHealthLabels.set(getLabelsAsString(recipe.getHealthLabels()));
                recipeDietLabels.set(getLabelsAsString(recipe.getDietLabels()));
                recipeIngredients.set(getIngredientsAsString(recipe.getIngredients()));
            }
        }
    }

    public void onStart() {
        favoriteImageStateChecked.set(repository.isInFavorites(recipe));
    }

    private String getIngredientsAsString(Collection<Ingredient> ingredients) {
        String ingredientsAsString = "";

        for (Ingredient ingredient : ingredients) {
            if (!ingredientsAsString.isEmpty())
                ingredientsAsString = ingredientsAsString.concat(",\n");

            ingredientsAsString = ingredientsAsString.concat(ingredient.getText() + "; Weight: " + ingredient.getWeight());
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

    public void onFavoriteButtonClick(View view) {
        if (favoriteImageStateChecked.get())
            favoriteImageStateChecked.set(!repository.removeFromFavorites(recipe));
        else
            favoriteImageStateChecked.set(repository.addToFavorites(recipe));
    }

}
