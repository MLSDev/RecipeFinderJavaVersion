package com.mlsdev.recipefinder.data.source.local.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mlsdev.recipefinder.data.entity.nutrition.Nutrient;

import java.util.List;

@Dao
public interface NutrientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Nutrient> nutrients);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long createIfNotExist(Nutrient nutrient);

    @Query("select * from nutrients where id = :id")
    Nutrient loadById(long id);

    @Query("select * from nutrients where id in (:ids)")
    List<Nutrient> loadByIds(List<Integer> ids);

    @Query("delete from nutrients where id = :nutrientId")
    void deleteById(long nutrientId);

    @Delete
    void delete(Nutrient Nutrient);

    @Delete
    void delete(List<Nutrient> nutrients);

    @Query("delete from nutrients where id in (:ids)")
    void deleteByIds(List<Integer> ids);

}
