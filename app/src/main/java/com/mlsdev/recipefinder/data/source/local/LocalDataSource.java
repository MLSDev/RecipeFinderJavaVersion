package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;

import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.entity.Recipe;
import com.mlsdev.recipefinder.data.source.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class LocalDataSource extends BaseDataSource implements DataSource {
    private DataBaseHelper dataBaseHelper;

    public LocalDataSource(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public Observable<List<Recipe>> getFavorites() {
        List<Recipe> favoriteRecipes = new ArrayList<>();

        try {
            favoriteRecipes = dataBaseHelper.getRecipeDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Observable.from(favoriteRecipes).toList();
    }

    @Override
    public Observable<Boolean> addToFavorites(Recipe favoriteRecipe) {
        boolean result = false;
        try {
            result = dataBaseHelper.getRecipeDao().create(favoriteRecipe) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Observable.from(new Boolean[]{result});
    }

    @Override
    public Observable<Boolean> removeFromFavorites(Recipe removedRecipe) {
        boolean result = false;
        try {
            result = dataBaseHelper.getRecipeDao().delete(removedRecipe) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Observable.from(new Boolean[]{result});
    }
}
