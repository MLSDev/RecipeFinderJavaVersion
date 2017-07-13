package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Ingredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void createIfNotExist(Ingredient ingredient);

    @Query("select * from ingredients where recipe_uri = :uri")
    List<Ingredient> loadByRecipeUri(String uri);

    @Query("delete from ingredients where recipe_uri = :uri")
    void deleteByRecipeUri(String uri);
}
