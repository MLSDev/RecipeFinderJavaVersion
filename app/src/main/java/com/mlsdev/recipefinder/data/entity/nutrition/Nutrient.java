package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mlsdev.recipefinder.view.utils.Utils;

import java.io.Serializable;

@DatabaseTable(tableName = "analyzed_nutrients")
public class Nutrient implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    @SerializedName("label")
    private String label;
    @DatabaseField
    @SerializedName("quantity")
    private double quantity;
    @SerializedName("unit")
    @DatabaseField
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
        return label + " " + Utils.formatDecimalToString(quantity) + " " + unit;
    }

    public Nutrient() {
    }

    public Nutrient(String label, double quantity, String unit) {
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }
}
