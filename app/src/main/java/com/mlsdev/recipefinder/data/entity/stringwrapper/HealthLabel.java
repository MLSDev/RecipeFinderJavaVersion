package com.mlsdev.recipefinder.data.entity.stringwrapper;

import com.j256.ormlite.table.DatabaseTable;
import com.mlsdev.recipefinder.data.entity.Recipe;

@DatabaseTable(tableName = "health_label")
public class HealthLabel extends Label {
    public HealthLabel(String value, Recipe recipe) {
        super(value, recipe);
    }

    public HealthLabel() {
    }
}
