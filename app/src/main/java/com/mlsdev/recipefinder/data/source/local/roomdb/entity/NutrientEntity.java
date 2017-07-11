package com.mlsdev.recipefinder.data.source.local.roomdb.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.mlsdev.recipefinder.data.entity.nutrition.Nutrient;

import java.io.Serializable;

@Entity(tableName = "nutrients")
public class NutrientEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public final String label;
    public final double quantity;
    public final String unit;

    public NutrientEntity(Nutrient nutrient) {
        label = nutrient.getLabel();
        quantity = nutrient.getQuantity();
        unit = nutrient.getUnit();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
