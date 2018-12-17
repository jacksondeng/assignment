package com.gemalto.gemaltoapi.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class Picture {
    @SerializedName("large")
    private String profilePicUrl = "";

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
