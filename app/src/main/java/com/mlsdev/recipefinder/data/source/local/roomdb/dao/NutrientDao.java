package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.source.local.roomdb.entity.NutrientEntity;

import java.util.List;

@Dao
public interface NutrientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NutrientEntity> nutrientEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    int createIfNotExist(NutrientEntity nutrientEntity);

    @Query("select * from nutrients where id in (:ids)")
    List<NutrientEntity> loadByIds(List<Integer> ids);

}
