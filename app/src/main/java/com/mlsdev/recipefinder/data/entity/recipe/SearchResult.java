package com.mlsdev.recipefinder.data.entity.recipe;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    @SerializedName("q")
    private String query;
    @SerializedName("from")
    private int from;
    @SerializedName("to")
    private int to;
    @SerializedName("params")
    private Params params;
    @SerializedName("more")
    private boolean more;
    @SerializedName("count")
    private int count;
    @SerializedName("hits")
    private List<Hit> hits = new ArrayList<>();

    public String getQuery() {
        return query;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Params getParams() {
        return params;
    }

    public boolean isMore() {
        return more;
    }

    public int getCount() {
        return count;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
