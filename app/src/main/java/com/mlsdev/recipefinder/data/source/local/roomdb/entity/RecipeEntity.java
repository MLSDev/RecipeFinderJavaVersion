package com.mlsdev.recipefinder.data.source.local.roomdb.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.local.roomdb.converter.Converter;

import java.util.List;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = TotalNutrientsEntity.class,
                parentColumns = "id",
                childColumns = "total_nutrients_id")
})
@TypeConverters(Converter.class)
public class RecipeEntity {
    @PrimaryKey
    public final String uri;

    @ColumnInfo(name = "diet_labels")
    public final List<String> dietLabelIds;

    @ColumnInfo(name = "health_labels")
    public final List<String> healthLabelIds;

    @ColumnInfo(name = "total_nutrients_id")
    public final long totalNutrientsId;

    public final String label;
    public final String image;
    public final double yield;
    public final double calories;
    public final double totalWeight;

    public RecipeEntity(Recipe recipe, long totalNutrientsId) {
        uri = recipe.getUri();
        dietLabelIds = recipe.getDietLabels();
        healthLabelIds = recipe.getHealthLabels();
        label = recipe.getLabel();
        image = recipe.getImage();
        yield = recipe.getYield();
        calories = recipe.getCalories();
        totalWeight = recipe.getTotalWeight();
        this.totalNutrientsId = totalNutrientsId;
    }
}
