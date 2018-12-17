package com.gemalto.gemaltoapi.db;

import android.arch.persistence.room.TypeConverter;

import com.gemalto.gemaltoapi.data.Dob;
import com.gemalto.gemaltoapi.data.Info;
import com.gemalto.gemaltoapi.data.Picture;
import com.gemalto.gemaltoapi.data.Username;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by jacksondeng on 15/12/18.
 */


public class Converters {
    @TypeConverter
    public static Username stringtoUsername(String username) {
        Type type = new TypeToken<Username>(){}.getType();
        return new Gson().fromJson(username,type);
    }

    @TypeConverter
    public static String UsernameToString(Username username) {
        Gson gson = new Gson();
        return gson.toJson(username);
    }

    @TypeConverter
    public static Dob stringToDob(String dob){
        Type type = new TypeToken<Dob>(){}.getType();
        return new Gson().fromJson(dob,type);
    }

    @TypeConverter
    public static String DobToString(Dob dob){
        Gson gson = new Gson();
        return gson.toJson(dob);
    }

    @TypeConverter
    public static Info stringToInfo(String info){
        Type type = new TypeToken<Info>(){}.getType();
        return new Gson().fromJson(info,type);
    }

    @TypeConverter
    public static String InfoToString(Info info){
        Gson gson = new Gson();
        return gson.toJson(info);
    }

    @TypeConverter
    public static Picture stringToPicture(String picture){
        Type type = new TypeToken<Picture>(){}.getType();
        return new Gson().fromJson(picture,type);
    }

    @TypeConverter
    public static String ProfilePicToString(Picture picture){
        Gson gson = new Gson();
        return gson.toJson(picture);
    }

}