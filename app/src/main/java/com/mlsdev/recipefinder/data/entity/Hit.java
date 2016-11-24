package com.mlsdev.recipefinder.data.entity;

import com.google.gson.annotations.SerializedName;

public class Hit {
    @SerializedName("recipe")
    private Recipe recipe;
    @SerializedName("bookmarked")
    private boolean bookmarked;
    @SerializedName("bought")
    private boolean bought;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public boolean isBought() {
        return bought;
    }
}
