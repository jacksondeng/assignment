package com.gemalto.assignment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class Username {
    @SerializedName("title")
    private String title;
    @SerializedName("first")
    private String firstName;
    @SerializedName("last")
    private String lastName;

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
