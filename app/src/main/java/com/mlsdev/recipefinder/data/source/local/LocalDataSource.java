package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

public class LocalDataSource extends BaseDataSource implements DataSource {
    private DataBaseHelper dataBaseHelper;

    public LocalDataSource(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public Observable<List<Recipe>> getFavorites() {
        return Observable.fromCallable(new Callable<List<Recipe>>() {
            @Override
            public List<Recipe> call() throws Exception {
                List<Recipe> favoriteRecipes = new ArrayList<>();

                try {
                    favoriteRecipes = dataBaseHelper.getRecipeDao().queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return favoriteRecipes;
            }
        });
    }

    @Override
    public Observable<Boolean> addToFavorites(final Recipe favoriteRecipe) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
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
        });
    }

    @Override
    public Observable<Boolean> removeFromFavorites(final Recipe removedRecipe) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean result = false;
                try {
                    result = dataBaseHelper.getRecipeDao().delete(removedRecipe) == 1;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }

    @Override
    public Observable<Boolean> isInFavorites(final Recipe recipe) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
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
        });
    }
}
