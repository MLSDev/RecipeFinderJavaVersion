package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Nutrient {
    @SerializedName("label")
    private String label;
    @SerializedName("quantity")
    private double quantity;
    @SerializedName("unit")
    private String unit;

    public String getLabel() {
        return label;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getFormattedFullText() {
        return label + " " + new DecimalFormat("#0.00").format(quantity) + " " + unit;
    }

    public Nutrient() {
    }

    public Nutrient(String label, double quantity, String unit) {
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }
}
