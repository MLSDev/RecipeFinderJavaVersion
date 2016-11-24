package com.mlsdev.recipefinder.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    @SerializedName("uri")
    private String uri;
    @SerializedName("label")
    private String label;
    @SerializedName("image")
    private String image;
    @SerializedName("source")
    private String source;
    @SerializedName("sourceIcon")
    private String sourceIcon;
    @SerializedName("url")
    private String url;
    @SerializedName("shareAs")
    private String shareAs;
    @SerializedName("yield")
    private double yield;
    @SerializedName("calories")
    private double calories;
    @SerializedName("totalWeight")
    private double totalWeight;
    @SerializedName("totalNutrients")
    private TotalNutrients totalNutrients;
    @SerializedName("totalDaily")
    private TotalDaily totalDaily;
    @SerializedName("dietLabels")
    private List<String> dietLabels = new ArrayList<>();
    @SerializedName("healthLabels")
    private List<String> healthLabels = new ArrayList<>();
    @SerializedName("cautions")
    private List<String> cautions = new ArrayList<>();
    @SerializedName("ingredientLines")
    private List<String> ingredientLines = new ArrayList<>();
    @SerializedName("ingredients")
    private List<Ingredient> ingredients = new ArrayList<>();

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

    public String getSourceIcon() {
        return sourceIcon;
    }

    public String getUrl() {
        return url;
    }

    public String getShareAs() {
        return shareAs;
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

    public TotalDaily getTotalDaily() {
        return totalDaily;
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

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
