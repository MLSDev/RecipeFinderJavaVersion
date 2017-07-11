package com.mlsdev.recipefinder.data.source.local.roomdb.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = NutrientEntity.class,
                parentColumns = "id",
                childColumns = "energy_id"),
        @ForeignKey(
                entity = NutrientEntity.class,
                parentColumns = "id",
                childColumns = "fat_id"),
        @ForeignKey(
                entity = NutrientEntity.class,
                parentColumns = "id",
                childColumns = "carbs_id"),
        @ForeignKey(
                entity = NutrientEntity.class,
                parentColumns = "id",
                childColumns = "protein_id")
})
public class TotalNutrientsEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "energy_id")
    public final long energyLabelId;

    @ColumnInfo(name = "fat_id")
    public final long fatLabelId;

    @ColumnInfo(name = "carbs_id")
    public final long carbsLabelId;

    @ColumnInfo(name = "protein_id")
    public final long proteinLabelId;

    public TotalNutrientsEntity(long energyLabelId, long fatLabelId, long carbsLabelId,
                                long proteinLabelId) {
        this.energyLabelId = energyLabelId;
        this.fatLabelId = fatLabelId;
        this.carbsLabelId = carbsLabelId;
        this.proteinLabelId = proteinLabelId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
