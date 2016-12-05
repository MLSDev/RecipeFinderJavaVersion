package com.mlsdev.recipefinder.data.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.mlsdev.recipefinder.data.entity.stringwrapper.Label;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Recipe implements Serializable {
    @DatabaseField(id = true)
    @SerializedName("uri")
    private String uri;
    @DatabaseField
    @SerializedName("label")
    private String label;
    @DatabaseField
    @SerializedName("image")
    private String image;
    @DatabaseField
    @SerializedName("source")
    private String source;
    @DatabaseField(columnName = "source_icon")
    @SerializedName("sourceIcon")
    private String sourceIcon;
    @DatabaseField
    @SerializedName("url")
    private String url;
    @DatabaseField(columnName = "share_as")
    @SerializedName("shareAs")
    private String shareAs;
    @DatabaseField
    @SerializedName("yield")
    private double yield;
    @DatabaseField
    @SerializedName("calories")
    private double calories;
    @DatabaseField(columnName = "total_weight")
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

    @ForeignCollectionField(columnName = "labels", eager = true)
    private Collection<Label> healthLabelCollection = new ArrayList<>();

    @ForeignCollectionField(eager = true)
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
        if (healthLabels.isEmpty() && !healthLabelCollection.isEmpty()) {
            for (Label label : healthLabelCollection)
                healthLabels.add(label.getValue());
        } else if (!healthLabels.isEmpty() && healthLabelCollection.isEmpty()){
            for (String label : healthLabels)
                healthLabelCollection.add(new Label(label, this));
        }

        return healthLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }


    public Collection<Label> getHealthLabelCollection() {
        if (!healthLabels.isEmpty()) {
            healthLabelCollection.clear();

            for (String healthLabel : healthLabels)
                healthLabelCollection.add(new Label(healthLabel, this));
        }

        return healthLabelCollection;
    }

    public void setHealthLabelCollection(Collection<Label> healthLabelCollection) {
        this.healthLabelCollection = healthLabelCollection;
    }

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

}
