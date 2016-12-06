package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocalDataSource extends BaseDataSource implements DataSource {
    private DataBaseHelper dataBaseHelper;

    public LocalDataSource(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public List<Recipe> getFavorites() {
        List<Recipe> favoriteRecipes = new ArrayList<>();

        try {
            favoriteRecipes = dataBaseHelper.getRecipeDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteRecipes;
    }

    @Override
    public boolean addToFavorites(Recipe favoriteRecipe) {
        boolean result = false;
        try {
            for (Ingredient ingredient : favoriteRecipe.getIngredients())
                ingredient.setRecipe(favoriteRecipe);

            dataBaseHelper.getHealthLabelDao().create(favoriteRecipe.getHealthLabelCollection());
            dataBaseHelper.getDietLabelDao().create(favoriteRecipe.getDietLabelCollection());
            dataBaseHelper.getIngredientDao().create(favoriteRecipe.getIngredients());
            result = dataBaseHelper.getRecipeDao().createIfNotExists(favoriteRecipe) != null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean removeFromFavorites(Recipe removedRecipe) {
        boolean result = false;
        try {
            result = dataBaseHelper.getRecipeDao().delete(removedRecipe) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean isInFavorites(Recipe recipe) {
        if (recipe == null)
            return false;

        boolean result = false;

        try {
            result = dataBaseHelper.getRecipeDao().idExists(recipe.getUri());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
