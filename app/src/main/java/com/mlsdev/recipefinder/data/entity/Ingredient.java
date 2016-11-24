package com.mlsdev.recipefinder.data.entity;


import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("text")
    private String text;
    @SerializedName("weight")
    private double weight;

    public String getText() {
        return text;
    }

    public double getWeight() {
        return weight;
    }
}
