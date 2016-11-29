package com.mlsdev.recipefinder.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeString(this.label);
        dest.writeString(this.image);
        dest.writeString(this.source);
        dest.writeString(this.sourceIcon);
        dest.writeString(this.url);
        dest.writeString(this.shareAs);
        dest.writeDouble(this.yield);
        dest.writeDouble(this.calories);
        dest.writeDouble(this.totalWeight);
        dest.writeParcelable(this.totalNutrients, flags);
        dest.writeParcelable(this.totalDaily, flags);
        dest.writeStringList(this.dietLabels);
        dest.writeStringList(this.healthLabels);
        dest.writeStringList(this.cautions);
        dest.writeStringList(this.ingredientLines);
        dest.writeTypedList(this.ingredients);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.uri = in.readString();
        this.label = in.readString();
        this.image = in.readString();
        this.source = in.readString();
        this.sourceIcon = in.readString();
        this.url = in.readString();
        this.shareAs = in.readString();
        this.yield = in.readDouble();
        this.calories = in.readDouble();
        this.totalWeight = in.readDouble();
        this.totalNutrients = in.readParcelable(TotalNutrients.class.getClassLoader());
        this.totalDaily = in.readParcelable(TotalDaily.class.getClassLoader());
        this.dietLabels = in.createStringArrayList();
        this.healthLabels = in.createStringArrayList();
        this.cautions = in.createStringArrayList();
        this.ingredientLines = in.createStringArrayList();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
