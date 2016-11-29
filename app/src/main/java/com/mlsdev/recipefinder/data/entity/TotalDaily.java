package com.mlsdev.recipefinder.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TotalDaily implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public TotalDaily() {
    }

    protected TotalDaily(Parcel in) {
    }

    public static final Creator<TotalDaily> CREATOR = new Creator<TotalDaily>() {
        @Override
        public TotalDaily createFromParcel(Parcel source) {
            return new TotalDaily(source);
        }

        @Override
        public TotalDaily[] newArray(int size) {
            return new TotalDaily[size];
        }
    };
}
