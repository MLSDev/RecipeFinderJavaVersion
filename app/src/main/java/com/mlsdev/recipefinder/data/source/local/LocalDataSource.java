package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Single;

public class LocalDataSource extends BaseDataSource implements DataSource {
    private DataBaseHelper dataBaseHelper;

    public LocalDataSource(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public Single<List<Recipe>> getFavorites() {
        return Single.fromCallable(new Callable<List<Recipe>>() {
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
    public Single<Boolean> addToFavorites(final Recipe favoriteRecipe) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean result = false;
//                try {
//                    for (Ingredient ingredient : favoriteRecipe.getIngredients())
//                        ingredient.setRecipe(favoriteRecipe);
//
//                    dataBaseHelper.getHealthLabelDao().create(favoriteRecipe.getHealthLabelCollection());
//                    dataBaseHelper.getDietLabelDao().create(favoriteRecipe.getDietLabelCollection());
//                    dataBaseHelper.getIngredientDao().create(favoriteRecipe.getIngredients());
//                    result = dataBaseHelper.getRecipeDao().createIfNotExists(favoriteRecipe) != null;
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }

                return result;
            }
        });
    }

    @Override
    public Single<Boolean> removeFromFavorites(final Recipe removedRecipe) {
        return Single.fromCallable(new Callable<Boolean>() {
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
    public Single<Boolean> isInFavorites(final Recipe recipe) {
        return Single.fromCallable(new Callable<Boolean>() {
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

    @Override
    public void addAnalyzingResult(final NutritionAnalysisResult analysisResult, Map<String, String> params) {
        if (!params.containsKey(ParameterKeys.INGREDIENT))
            return;

        int hash = params.get(ParameterKeys.INGREDIENT).toLowerCase().hashCode();
        analysisResult.setHash(hash);

        try {
            dataBaseHelper.getNutrientDao().create(analysisResult.getTotalNutrients().getCarbs());
            dataBaseHelper.getNutrientDao().create(analysisResult.getTotalNutrients().getFat());
            dataBaseHelper.getNutrientDao().create(analysisResult.getTotalNutrients().getEnergy());
            dataBaseHelper.getNutrientDao().create(analysisResult.getTotalNutrients().getProtein());
            dataBaseHelper.getTotalNutrientsDao().create(analysisResult.getTotalNutrients());
            dataBaseHelper.getIngredientAnalysisResultDao().createIfNotExists(analysisResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Single<NutritionAnalysisResult> getIngredientData(final Map<String, String> params) {
        if (params.containsKey(ParameterKeys.INGREDIENT)) {
            return Single.fromCallable(new Callable<NutritionAnalysisResult>() {
                @Override
                public NutritionAnalysisResult call() throws Exception {
                    int hash = params.get(ParameterKeys.INGREDIENT).toLowerCase().hashCode();
                    NutritionAnalysisResult result = null;

                    try {
                        result = dataBaseHelper.getIngredientAnalysisResultDao().queryForId(hash);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return result;
                }
            });
        } else {
            return super.getIngredientData(params);
        }
    }
}
