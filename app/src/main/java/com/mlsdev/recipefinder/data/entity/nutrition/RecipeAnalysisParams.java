package com.mlsdev.recipefinder.data.entity.nutrition;

import java.util.ArrayList;
import java.util.List;

public class RecipeAnalysisParams {
    private String title;
    private String prep;
    private String yield;
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
