package com.mlsdev.recipefinder.data.entity.recipe;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Params {
    @SerializedName("sane")
    private List<String> sane = new ArrayList<>();
    @SerializedName("to")
    private List<String> to = new ArrayList<>();
    @SerializedName("from")
    private List<String> from = new ArrayList<>();
    @SerializedName("q")
    private List<String> queries = new ArrayList<>();
    @SerializedName("calories")
    private List<String> calories = new ArrayList<>();
    @SerializedName("health")
    private List<String> health = new ArrayList<>();

    public List<String> getSane() {
        return sane;
    }

    public List<String> getTo() {
        return to;
    }

    public List<String> getFrom() {
        return from;
    }

    public List<String> getQueries() {
        return queries;
    }

    public List<String> getCalories() {
        return calories;
    }

    public List<String> getHealth() {
        return health;
    }
}
