package com.mlsdev.recipefinder.data.entity.nutrition;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.mlsdev.recipefinder.view.utils.Utils;

@Entity(tableName = "nutrients")
public class Nutrient {
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
        return label + " " + Utils.formatDecimalToString(quantity) + " " + unit;
    }

}
