package com.mlsdev.recipefinder.data.entity.stringwrapper;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mlsdev.recipefinder.data.entity.Recipe;

import java.io.Serializable;

public abstract class Label implements Serializable {
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    protected Recipe recipe;
    @DatabaseField(generatedId = true)
    protected long id;
    @DatabaseField
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
