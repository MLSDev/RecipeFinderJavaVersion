package com.mlsdev.recipefinder.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mlsdev.recipefinder.data.entity.nutrition.IngredientAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.Nutrient;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.stringwrapper.DietLabel;
import com.mlsdev.recipefinder.data.entity.recipe.stringwrapper.HealthLabel;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "local_data.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<Recipe, String> recipeDao;
    private Dao<HealthLabel, Integer> healthLabelDao;
    private Dao<DietLabel, Integer> dietLabelDao;
    private Dao<Ingredient, Integer> ingredientDao;
    private Dao<IngredientAnalysisResult, Integer> ingredientAnalysisResultDao;
    private Dao<TotalNutrients, Integer> totalNutrientsDao;
    private Dao<Nutrient, Integer> nutrientDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Recipe.class);
            TableUtils.createTable(connectionSource, HealthLabel.class);
            TableUtils.createTable(connectionSource, DietLabel.class);
            TableUtils.createTable(connectionSource, Ingredient.class);
            TableUtils.createTable(connectionSource, TotalNutrients.class);
            TableUtils.createTable(connectionSource, Nutrient.class);
            TableUtils.createTable(connectionSource, IngredientAnalysisResult.class);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Unable to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Recipe.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }

    public Dao<Recipe, String> getRecipeDao() throws SQLException {
        if (recipeDao == null)
            recipeDao = getDao(Recipe.class);

        return recipeDao;
    }

    public Dao<HealthLabel, Integer> getHealthLabelDao() throws SQLException {
        if (healthLabelDao == null)
            healthLabelDao = getDao(HealthLabel.class);

        return healthLabelDao;
    }

    public Dao<DietLabel, Integer> getDietLabelDao() throws SQLException {
        if (dietLabelDao == null)
            dietLabelDao = getDao(DietLabel.class);

        return dietLabelDao;
    }

    public Dao<Ingredient, Integer> getIngredientDao() throws SQLException {
        if (ingredientDao == null)
            ingredientDao = getDao(Ingredient.class);

        return ingredientDao;
    }

    public Dao<IngredientAnalysisResult, Integer> getIngredientAnalysisResultDao() throws SQLException {
        if (ingredientAnalysisResultDao == null)
            ingredientAnalysisResultDao = getDao(IngredientAnalysisResult.class);

        return ingredientAnalysisResultDao;
    }

    public Dao<TotalNutrients, Integer> getTotalNutrientsDao() throws SQLException {
        if (totalNutrientsDao == null)
            totalNutrientsDao = getDao(TotalNutrients.class);

        return totalNutrientsDao;
    }

    public Dao<Nutrient, Integer> getNutrientDao() throws SQLException {
        if (nutrientDao == null)
            nutrientDao = getDao(Nutrient.class);

        return nutrientDao;
    }

    @Override
    public void close() {
        super.close();
        recipeDao = null;
        healthLabelDao = null;
        dietLabelDao = null;
        ingredientDao = null;
        ingredientAnalysisResultDao = null;
        totalNutrientsDao = null;
        nutrientDao = null;
    }
}
