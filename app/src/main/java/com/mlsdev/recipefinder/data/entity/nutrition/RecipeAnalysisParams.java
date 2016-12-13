package com.mlsdev.recipefinder.data.entity.nutrition;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeAnalysisParams {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("prep")
    @Expose
    private String prep;
    @SerializedName("yield")
    @Expose
    private String yield;
    @SerializedName("ingr")
    @Expose
    private List<String> ingr = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrep(String prep) {
        this.prep = prep;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public void setIngr(List<String> ingr) {
        this.ingr = ingr;
    }
}
