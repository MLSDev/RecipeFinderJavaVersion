package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Recipe recipe);

    @Query("select uri from recipes where uri = :recipeUri")
    Flowable<Recipe> loadByUri(String recipeUri);

    @Query("select * from recipes")
    Flowable<List<Recipe>> loadAll();

    @Delete
    int delete(Recipe recipe);

    @Delete
    void delete(List<Recipe> recipes);

    @Query("delete from recipes where uri = :uri")
    void deleteByIds(String uri);

}
