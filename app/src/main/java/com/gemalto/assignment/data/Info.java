package com.gemalto.assignment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class Info {
    @SerializedName("seed")
    private String seed="";
    @SerializedName("page")
    private int page=1;
    @SerializedName("results")
    private int results=1;

    public String getSeed() {
        return seed;
    }

    public int getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }
}
