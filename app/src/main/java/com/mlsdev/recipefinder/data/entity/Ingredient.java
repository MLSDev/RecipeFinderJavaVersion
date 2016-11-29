package com.mlsdev.recipefinder.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeDouble(this.weight);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.text = in.readString();
        this.weight = in.readDouble();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
