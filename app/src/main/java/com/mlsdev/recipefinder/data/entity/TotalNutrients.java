package com.mlsdev.recipefinder.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TotalNutrients implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public TotalNutrients() {
    }

    protected TotalNutrients(Parcel in) {
    }

    public static final Creator<TotalNutrients> CREATOR = new Creator<TotalNutrients>() {
        @Override
        public TotalNutrients createFromParcel(Parcel source) {
            return new TotalNutrients(source);
        }

        @Override
        public TotalNutrients[] newArray(int size) {
            return new TotalNutrients[size];
        }
    };
}
