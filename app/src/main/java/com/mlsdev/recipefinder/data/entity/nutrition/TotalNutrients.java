package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "total_nutrients")
public class TotalNutrients implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @SerializedName("ENERC_KCAL")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Nutrient energy;
    @SerializedName("FAT")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Nutrient fat;
    @SerializedName("CHOCDF")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Nutrient carbs;
    @SerializedName("PROCNT")
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Nutrient protein;

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

    public TotalNutrients() {
    }
}
