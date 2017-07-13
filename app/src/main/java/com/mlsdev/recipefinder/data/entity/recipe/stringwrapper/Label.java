package com.mlsdev.recipefinder.data.entity.recipe.stringwrapper;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;

import java.io.Serializable;

public abstract class Label implements Serializable {
    protected Recipe recipe;
    protected long id;
    protected String value;

    public Label() {
    }

    public Label(String value, Recipe recipe) {
        this.recipe = recipe;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
