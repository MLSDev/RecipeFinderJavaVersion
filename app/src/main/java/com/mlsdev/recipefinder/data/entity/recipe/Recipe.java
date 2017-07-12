package com.mlsdev.recipefinder.data.entity.recipe;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.source.local.roomdb.converter.Converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "recipes",
        foreignKeys = {
                @ForeignKey(
                        entity = TotalNutrients.class,
                        parentColumns = "id",
                        childColumns = "total_nutrients_id")},
        indices = {@Index("total_nutrients_id")}
)
@TypeConverters(Converter.class)
public class Recipe implements Serializable {
    @PrimaryKey
    private String uri;
    private String label;
    private String image;
    private String source;
    private double yield;
    private double calories;

    @SerializedName("totalWeight")
    private double totalWeight;

    @Ignore
    @SerializedName("totalNutrients")
    private TotalNutrients totalNutrients;

    @ColumnInfo(name = "total_nutrients_id")
    private long totalNutrientsId;

    @ColumnInfo(name = "diet_labels")
    @SerializedName("dietLabels")
    private List<String> dietLabels = new ArrayList<>();

    @ColumnInfo(name = "health_labels")
    @SerializedName("healthLabels")
    private List<String> healthLabels = new ArrayList<>();

    @Ignore
    @SerializedName("ingredients")
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    @Ignore
    public Recipe(long totalNutrientsId) {
        this.totalNutrientsId = totalNutrientsId;
    }

    // region Getters

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

    public long getTotalNutrientsId() {
        return totalNutrientsId;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    // endregion

    // region Setters

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setTotalNutrients(TotalNutrients totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public void setTotalNutrientsId(long totalNutrientsId) {
        this.totalNutrientsId = totalNutrientsId;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // endregion

}
