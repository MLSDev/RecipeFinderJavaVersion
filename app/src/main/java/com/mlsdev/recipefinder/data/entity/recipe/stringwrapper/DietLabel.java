package com.mlsdev.recipefinder.data.entity.recipe.stringwrapper;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;

public class DietLabel extends Label {
    public DietLabel() {
    }

    public DietLabel(String value, Recipe recipe) {
        super(value, recipe);
    }
}
