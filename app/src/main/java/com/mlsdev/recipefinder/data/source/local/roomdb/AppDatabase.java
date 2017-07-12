package com.mlsdev.recipefinder.data.source.local.roomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mlsdev.recipefinder.data.entity.nutrition.Nutrient;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.local.roomdb.dao.NutrientDao;
import com.mlsdev.recipefinder.data.source.local.roomdb.dao.RecipeDao;
import com.mlsdev.recipefinder.data.source.local.roomdb.dao.TotalNutrientsDao;

@Database(
        entities = {
                Recipe.class,
                TotalNutrients.class,
                Nutrient.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract RecipeDao recipeDao();

    public abstract NutrientDao nutrientDao();

    public abstract TotalNutrientsDao totalNutrientsDao();

    public static AppDatabase getDb() {
        return INSTANCE;
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "recipes.db")
                            .build();
        }
    }
}
