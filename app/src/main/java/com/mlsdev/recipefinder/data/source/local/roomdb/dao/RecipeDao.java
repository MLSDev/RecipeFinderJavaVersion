package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.source.local.roomdb.entity.RecipeEntity;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeEntity recipe);

    @Query("select * from recipes")
    List<RecipeEntity> loadAll();

}
