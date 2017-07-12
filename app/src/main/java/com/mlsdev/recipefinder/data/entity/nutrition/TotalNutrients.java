package com.mlsdev.recipefinder.data.entity.nutrition;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "total_nutrients",
        foreignKeys = {
                @ForeignKey(
                        entity = Nutrient.class,
                        parentColumns = "id",
                        childColumns = "energy_id"),
                @ForeignKey(
                        entity = Nutrient.class,
                        parentColumns = "id",
                        childColumns = "fat_id"),
                @ForeignKey(
                        entity = Nutrient.class,
                        parentColumns = "id",
                        childColumns = "carbs_id"),
                @ForeignKey(
                        entity = Nutrient.class,
                        parentColumns = "id",
                        childColumns = "protein_id")},
        indices = {
                @Index("energy_id"),
                @Index("fat_id"),
                @Index("carbs_id"),
                @Index("protein_id")
        }
)
public class TotalNutrients implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Ignore
    @SerializedName("ENERC_KCAL")
    private Nutrient energy;

    @Ignore
    @SerializedName("FAT")
    private Nutrient fat;

    @Ignore
    @SerializedName("CHOCDF")
    private Nutrient carbs;

    @Ignore
    @SerializedName("PROCNT")
    private Nutrient protein;

    @ColumnInfo(name = "energy_id")
    private long energyNutrientId;

    @ColumnInfo(name = "fat_id")
    private long fatNutrientId;

    @ColumnInfo(name = "carbs_id")
    private long carbsNutrientId;

    @ColumnInfo(name = "protein_id")
    private long proteinNutrientId;

    public TotalNutrients() {
    }

    // region Getters
    public Nutrient getEnergy() {
        return energy;
    }

    public Nutrient getFat() {
        return fat;
    }

    public Nutrient getCarbs() {
        return carbs;
    }

    public Nutrient getProtein() {
        return protein;
    }

    public long getId() {
        return id;
    }

    public long getEnergyNutrientId() {
        return energyNutrientId;
    }

    public long getFatNutrientId() {
        return fatNutrientId;
    }

    public long getCarbsNutrientId() {
        return carbsNutrientId;
    }

    public long getProteinNutrientId() {
        return proteinNutrientId;
    }

    // endregion

    // region Setters

    public void setId(long id) {
        this.id = id;
    }

    public void setEnergyNutrientId(long energyNutrientId) {
        this.energyNutrientId = energyNutrientId;
    }

    public void setFatNutrientId(long fatNutrientId) {
        this.fatNutrientId = fatNutrientId;
    }

    public void setCarbsNutrientId(long carbsNutrientId) {
        this.carbsNutrientId = carbsNutrientId;
    }

    public void setProteinNutrientId(long proteinNutrientId) {
        this.proteinNutrientId = proteinNutrientId;
    }

    public void setEnergy(Nutrient energy) {
        this.energy = energy;
    }

    public void setFat(Nutrient fat) {
        this.fat = fat;
    }

    public void setCarbs(Nutrient carbs) {
        this.carbs = carbs;
    }

    public void setProtein(Nutrient protein) {
        this.protein = protein;
    }

    // endregion
}
