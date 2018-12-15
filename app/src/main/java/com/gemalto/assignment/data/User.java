package com.gemalto.assignment.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import timber.log.Timber;

/**
 * Created by jacksondeng on 15/12/18.
 */


@Entity(tableName = "User")
public class User implements Parcelable{
    @SerializedName("name")
    private Username username;
    @SerializedName("gender")
    private String gender="";
    @SerializedName("dob")
    private Dob dob;
    @SerializedName("email")
    private String email="";
    @PrimaryKey
    @NonNull
    private String seed="";
    @SerializedName("picture")
    @Ignore
    private Picture picture;
    private String profilePicUrl ="";

    // isStored == 0 -> not stored
    private int isStored= 0;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getrealUsername() {
        return username.getTitle()+ " " +
                username.getFirstName() + " "
                + username.getLastName();
    }

    public void setName(Username name) {
        this.username = name;
    }

    public String getDobStr() {
        return dob.getDob();
    }

    public int getAge() {
        return dob.getAge();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setIsStored(int isStored) {
        this.isStored = isStored;
    }

    public int getIsStored() {
        return isStored;
    }

    public Username getUsername(){
        return username;
    }

    public void setUsername(Username username){
        this.username = username;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public String getSeed() {
        return seed;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(seed);
        parcel.writeString(getrealUsername());
        parcel.writeString(getGender());
        parcel.writeString(getDobStr());
        parcel.writeString(getEmail());
        parcel.writeInt(getIsStored());
        if(picture!=null) {
            parcel.writeString(picture.getProfilePicUrl());
        }else{
            parcel.writeString(getProfilePicUrl());
        }
    }

    public User(){

    }

    public User(Parcel parcelIn){
        this.seed = parcelIn.readString();
        this.username = new Username(parcelIn.readString());
        this.gender = parcelIn.readString();
        this.dob = new Dob(parcelIn.readString());
        this.email = parcelIn.readString();
        this.isStored = parcelIn.readInt();
        this.profilePicUrl = parcelIn.readString();
    }
}
