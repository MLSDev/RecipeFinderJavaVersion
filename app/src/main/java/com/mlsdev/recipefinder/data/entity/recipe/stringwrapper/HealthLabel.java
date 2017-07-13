package com.mlsdev.recipefinder.data.entity.recipe.stringwrapper;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;

public class HealthLabel extends Label {
    public HealthLabel(String value, Recipe recipe) {
        super(value, recipe);
    }

    public HealthLabel() {
    }
}
