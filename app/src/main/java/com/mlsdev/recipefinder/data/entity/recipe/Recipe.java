package com.mlsdev.recipefinder.data.entity.recipe;

import com.google.gson.annotations.SerializedName;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Recipe implements Serializable {
    @SerializedName("uri")
    private String uri;

    @SerializedName("label")
    private String label;

    @SerializedName("image")
    private String image;

    @SerializedName("source")
    private String source;

    @SerializedName("yield")
    private double yield;

    @SerializedName("calories")
    private double calories;

    @SerializedName("totalWeight")
    private double totalWeight;

    @SerializedName("totalNutrients")
    private TotalNutrients totalNutrients;

    @SerializedName("dietLabels")
    private List<String> dietLabels = new ArrayList<>();

    @SerializedName("healthLabels")
    private List<String> healthLabels = new ArrayList<>();

    @SerializedName("ingredients")
    private Collection<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public String getUri() {
        return uri;
    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getSource() {
        return source;
    }

    public double getYield() {
        return yield;
    }

    public double getCalories() {
        return calories;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

}
