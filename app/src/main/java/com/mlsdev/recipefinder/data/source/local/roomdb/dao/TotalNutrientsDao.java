package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;

@Dao
public interface TotalNutrientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createIfNotExist(TotalNutrients totalNutrients);

    @Query("select * from total_nutrients where id = :totalNutrientsId")
    TotalNutrients loadById(long totalNutrientsId);

    @Query("delete from total_nutrients where id = :totalNutrientsId")
    void deleteById(long totalNutrientsId);

    @Delete
    void delete(TotalNutrients totalNutrients);
}
