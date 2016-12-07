package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IngredientAnalysisResult {
    @SerializedName("uri")
    private String uri;
    @SerializedName("calories")
    private int calories;
    @SerializedName("totalWeight")
    private double totalWeight;
    @SerializedName("dietLabels")
    private List<String> dietLabels = new ArrayList<>();
    @SerializedName("healthLabels")
    private List<String> healthLabels = new ArrayList<>();
    @SerializedName("cautions")
    private List<String> cautions = new ArrayList<>();
    @SerializedName("totalNutrients")
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
}
