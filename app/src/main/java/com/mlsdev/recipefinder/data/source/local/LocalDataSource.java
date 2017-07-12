package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Single;

public class LocalDataSource extends BaseDataSource implements DataSource {
    private DataBaseHelper dataBaseHelper;
    private AppDatabase db;

    public LocalDataSource(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        db = AppDatabase.getDb();
    }

    @Override
    public Single<List<Recipe>> getFavorites() {
        return Single.fromCallable(new Callable<List<Recipe>>() {
            @Override
            public List<Recipe> call() throws Exception {
                List<Recipe> favoriteRecipes = db.recipeDao().loadAll();

                for (Recipe recipe : favoriteRecipes) {
                    TotalNutrients totalNutrients = db.totalNutrientsDao().loadById(recipe.getTotalNutrientsId());
                    totalNutrients.setEnergy(db.nutrientDao().loadById(totalNutrients.getEnergyNutrientId()));
                    totalNutrients.setFat(db.nutrientDao().loadById(totalNutrients.getFatNutrientId()));
                    totalNutrients.setCarbs(db.nutrientDao().loadById(totalNutrients.getCarbsNutrientId()));
                    totalNutrients.setProtein(db.nutrientDao().loadById(totalNutrients.getProteinNutrientId()));

                    recipe.setTotalNutrients(totalNutrients);
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
                long energyNutrientId = db.nutrientDao().createIfNotExist(favoriteRecipe.getTotalNutrients().getEnergy());
                long fatNutrientId = db.nutrientDao().createIfNotExist(favoriteRecipe.getTotalNutrients().getFat());
                long carbsNutrientId = db.nutrientDao().createIfNotExist(favoriteRecipe.getTotalNutrients().getCarbs());
                long proteinNutrientId = db.nutrientDao().createIfNotExist(favoriteRecipe.getTotalNutrients().getProtein());

                favoriteRecipe.getTotalNutrients().setEnergyNutrientId(energyNutrientId);
                favoriteRecipe.getTotalNutrients().setFatNutrientId(fatNutrientId);
                favoriteRecipe.getTotalNutrients().setCarbsNutrientId(carbsNutrientId);
                favoriteRecipe.getTotalNutrients().setProteinNutrientId(proteinNutrientId);

                long totalNutrientsId = db.totalNutrientsDao().createIfNotExist(favoriteRecipe.getTotalNutrients());

                favoriteRecipe.setTotalNutrientsId(totalNutrientsId);
                return db.recipeDao().insert(favoriteRecipe) > -1;
            }
        });
    }

    @Override
    public Single<Boolean> removeFromFavorites(final Recipe removedRecipe) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                db.recipeDao().delete(removedRecipe);
                db.totalNutrientsDao().delete(removedRecipe.getTotalNutrients());
                db.nutrientDao().delete(removedRecipe.getTotalNutrients().getEnergy());
                db.nutrientDao().delete(removedRecipe.getTotalNutrients().getFat());
                db.nutrientDao().delete(removedRecipe.getTotalNutrients().getCarbs());
                db.nutrientDao().delete(removedRecipe.getTotalNutrients().getProtein());
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> isInFavorites(final Recipe recipe) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return recipe != null && db.recipeDao().loadByUri(recipe.getUri()) != null;
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
