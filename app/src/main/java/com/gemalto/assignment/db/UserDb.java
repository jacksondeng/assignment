package com.gemalto.assignment.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.gemalto.assignment.data.User;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jacksondeng on 15/12/18.
 */


@Database(entities = {User.class} , version =9 , exportSchema = false)
@TypeConverters({Converters.class})
@Singleton
public abstract class UserDb extends RoomDatabase{
    public abstract UserDao userDao();
}

