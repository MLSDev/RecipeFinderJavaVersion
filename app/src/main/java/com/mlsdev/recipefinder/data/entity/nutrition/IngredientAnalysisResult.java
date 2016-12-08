package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IngredientAnalysisResult implements Serializable {
    @DatabaseField(id = true)
    private int hash;
    @SerializedName("uri")
    @DatabaseField
    private String uri;
    @SerializedName("calories")
    @DatabaseField
    private int calories;
    @SerializedName("totalWeight")
    @DatabaseField
    private double totalWeight;
    @SerializedName("dietLabels")
    private List<String> dietLabels = new ArrayList<>();
    @SerializedName("healthLabels")
    private List<String> healthLabels = new ArrayList<>();
    @SerializedName("cautions")
    private List<String> cautions = new ArrayList<>();
    @SerializedName("totalNutrients")
    @DatabaseField(columnName = "total_nutrients", dataType = DataType.SERIALIZABLE)
    private TotalNutrients totalNutrients;

    public String getUri() {
        return uri;
    }

    public int getCalories() {
        return calories;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public IngredientAnalysisResult() {
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
}
