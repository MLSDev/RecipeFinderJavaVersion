package com.mlsdev.recipefinder.data.entity;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Ingredient implements Serializable {
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Recipe recipe;
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    @SerializedName("text")
    private String text;
    @DatabaseField
    @SerializedName("weight")
    private double weight;

    public String getText() {
        return text;
    }

    public double getWeight() {
        return weight;
    }

    public Ingredient() {
    }
}
