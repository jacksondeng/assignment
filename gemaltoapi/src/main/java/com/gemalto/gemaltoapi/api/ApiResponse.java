package com.gemalto.gemaltoapi.api;

import com.gemalto.gemaltoapi.data.Info;
import com.gemalto.gemaltoapi.data.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class ApiResponse {
    @SerializedName("results")
    private List<User> users;
    public List<User> getUser(){
        return this.users;
    }

    @SerializedName("info")
    private Info info;
    public Info getInfo(){
        return this.info;
    }
}
