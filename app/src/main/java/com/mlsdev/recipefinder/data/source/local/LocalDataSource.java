package com.mlsdev.recipefinder.data.source.local;

import android.support.annotation.NonNull;

import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseDataSource;
import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.local.roomdb.AppDatabase;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class LocalDataSource extends BaseDataSource implements DataSource {
    private AppDatabase db;

    public LocalDataSource(AppDatabase database) {
        db = database;
    }

    @Override
    public Flowable<List<Recipe>> getFavorites() {
        return db.recipeDao().loadAll()
                .map(new Function<List<Recipe>, List<Recipe>>() {
                    @Override
                    public List<Recipe> apply(@io.reactivex.annotations.NonNull List<Recipe> recipes) throws Exception {
                        for (Recipe recipe : recipes) {
                            TotalNutrients totalNutrients = db.totalNutrientsDao().loadById(recipe.getTotalNutrientsId());

                            if (totalNutrients == null)
                                continue;

                            totalNutrients.setEnergy(db.nutrientDao().loadById(totalNutrients.getEnergyNutrientId()));
                            totalNutrients.setFat(db.nutrientDao().loadById(totalNutrients.getFatNutrientId()));
                            totalNutrients.setCarbs(db.nutrientDao().loadById(totalNutrients.getCarbsNutrientId()));
                            totalNutrients.setProtein(db.nutrientDao().loadById(totalNutrients.getProteinNutrientId()));

                            recipe.setTotalNutrients(totalNutrients);
                            recipe.setIngredients(db.ingredientDao().loadByRecipeUri(recipe.getUri()));
                        }
                        return recipes;
                    }
                });
    }

    @Override
    public Completable addToFavorites(final Recipe favoriteRecipe) {
        return Completable.fromCallable(new Callable<Boolean>() {
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
    public Completable removeFromFavorites(final Recipe removedRecipe) {
        return Completable.fromCallable(new Callable<Boolean>() {
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
    public Single<Boolean> isInFavorites(@NonNull final Recipe recipe) {
        return db.recipeDao().loadByUri(recipe.getUri())
                .map(new Function<Recipe, Boolean>() {
                    @Override
                    public Boolean apply(@io.reactivex.annotations.NonNull Recipe recipe) throws Exception {
                        return recipe != null;
                    }
                })
                .first(false);
    }

}
