package com.mlsdev.recipefinder.data.entity.recipe.stringwrapper;

import com.j256.ormlite.table.DatabaseTable;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;

@DatabaseTable(tableName = "diet_label")
public class DietLabel extends Label {
    public DietLabel() {
    }

    public DietLabel(String value, Recipe recipe) {
        super(value, recipe);
    }
}
