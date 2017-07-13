package com.mlsdev.recipefinder.data.entity.nutrition;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NutritionAnalysisResult implements Parcelable {
    private String uri;
    private int calories;
    private double totalWeight;
    private double yield;
    private List<String> dietLabels = new ArrayList<>();
    private List<String> healthLabels = new ArrayList<>();
    private List<String> cautions = new ArrayList<>();
    private TotalNutrients totalNutrients;

    public String getUri() {
        return uri;
    }

    public int getCalories() {
        return calories;
    }

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public double getYield() {
        return yield;
    }

    public NutritionAnalysisResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri);
        dest.writeInt(this.calories);
        dest.writeDouble(this.totalWeight);
        dest.writeDouble(this.yield);
        dest.writeStringList(this.dietLabels);
        dest.writeStringList(this.healthLabels);
        dest.writeStringList(this.cautions);
        dest.writeParcelable(this.totalNutrients, flags);
    }

    protected NutritionAnalysisResult(Parcel in) {
        this.uri = in.readString();
        this.calories = in.readInt();
        this.totalWeight = in.readDouble();
        this.yield = in.readDouble();
        this.dietLabels = in.createStringArrayList();
        this.healthLabels = in.createStringArrayList();
        this.cautions = in.createStringArrayList();
        this.totalNutrients = in.readParcelable(TotalNutrients.class.getClassLoader());
    }

    public static final Creator<NutritionAnalysisResult> CREATOR = new Creator<NutritionAnalysisResult>() {
        @Override
        public NutritionAnalysisResult createFromParcel(Parcel source) {
            return new NutritionAnalysisResult(source);
        }

        @Override
        public NutritionAnalysisResult[] newArray(int size) {
            return new NutritionAnalysisResult[size];
        }
    };
}
