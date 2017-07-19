package com.mlsdev.recipefinder.data.entity.nutrition;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.mlsdev.recipefinder.view.utils.UtilsUI;

@Entity(tableName = "nutrients")
public class Nutrient implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String label;
    private double quantity;
    private String unit;

    public Nutrient() {
    }

    @Ignore
    public Nutrient(String label, double quantity, String unit) {
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }

    // region Getters
    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
    //endregion

    // region Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    // endregion

    public String getFormattedFullText() {
        return label + " " + UtilsUI.formatDecimalToString(quantity) + " " + unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.label);
        dest.writeDouble(this.quantity);
        dest.writeString(this.unit);
    }

    protected Nutrient(Parcel in) {
        this.id = in.readLong();
        this.label = in.readString();
        this.quantity = in.readDouble();
        this.unit = in.readString();
    }

    public static final Creator<Nutrient> CREATOR = new Creator<Nutrient>() {
        @Override
        public Nutrient createFromParcel(Parcel source) {
            return new Nutrient(source);
        }

        @Override
        public Nutrient[] newArray(int size) {
            return new Nutrient[size];
        }
    };
}
