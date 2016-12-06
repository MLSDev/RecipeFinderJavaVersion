package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.SerializedName;

public class TotalNutrients {
    @SerializedName("ENERC_KCAL")
    private Nutrient energy;
    @SerializedName("FAT")
    private Nutrient fat;
    @SerializedName("CHOCDF")
    private Nutrient carbs;
    @SerializedName("PROCNT")
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
}
