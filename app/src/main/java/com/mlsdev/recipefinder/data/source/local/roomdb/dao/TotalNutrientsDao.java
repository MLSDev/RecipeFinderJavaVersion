package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.source.local.roomdb.entity.TotalNutrientsEntity;

@Dao
public interface TotalNutrientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    int createIfNotExist(TotalNutrientsEntity totalNutrientsEntity);

    @Query("select * from recipes where id = :totalNutrientsId")
    TotalNutrientsEntity loadById(int totalNutrientsId);

}
