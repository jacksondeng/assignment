package com.gemalto.assignment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class Dob {
    @SerializedName("date")
    private String dob;
    @SerializedName("age")
    private int age;

    public int getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }
}
