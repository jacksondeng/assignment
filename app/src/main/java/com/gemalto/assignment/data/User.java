package com.gemalto.assignment.data;

import com.google.gson.annotations.SerializedName;

import timber.log.Timber;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class User {
    @SerializedName("name")
    private Username username;
    @SerializedName("gender")
    private String gender;
    @SerializedName("dob")
    private Dob dob;
    @SerializedName("email")
    private String email;
    @SerializedName("seed")
    private int seed;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username.getTitle()+ " " +
                username.getFirstName() + " "
                + username.getLastName();
    }

    public void setName(Username name) {
        this.username = name;
    }

    public String getDob() {
        return dob.getDob();
    }

    public int getAge() {
        return dob.getAge();
    }

    public String getEmail() {
        return email;
    }

    public int getSeed() {
        return seed;
    }

    public void printProfile(){
        Timber.d("Name " +getUsername() );
        Timber.d("Gender " +gender );
        Timber.d("Dob " +dob.getDob() );
        Timber.d("Age " +dob.getAge() );
        Timber.d("Email " +email );
        Timber.d("Seed " +seed );
    }
}
