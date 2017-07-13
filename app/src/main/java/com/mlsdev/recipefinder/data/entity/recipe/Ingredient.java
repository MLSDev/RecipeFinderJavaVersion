package com.mlsdev.recipefinder.data.entity.recipe;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ingredients")
public class Ingredient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "recipe_uri", index = true)
    private String recipeUri;

    private String text;
    private double weight;

    public Ingredient() {
    }

    public void setRecipeUri(String recipeUri) {
        this.recipeUri = recipeUri;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getRecipeUri() {
        return recipeUri;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public double getWeight() {
        return weight;
    }
}
