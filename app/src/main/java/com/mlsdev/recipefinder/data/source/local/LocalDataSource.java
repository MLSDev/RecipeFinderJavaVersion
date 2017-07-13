package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;

import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;

import java.util.List;
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
                    recipe.setIngredients(db.ingredientDao().loadByRecipeUri(recipe.getUri()));
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

                for (Ingredient ingredient : favoriteRecipe.getIngredients())
                    ingredient.setRecipeUri(favoriteRecipe.getUri());

                db.ingredientDao().insert(favoriteRecipe.getIngredients());

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
                db.ingredientDao().deleteByRecipeUri(removedRecipe.getUri());
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
}
