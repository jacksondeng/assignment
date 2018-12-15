package com.gemalto.assignment.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gemalto.assignment.data.User;

import java.util.List;

/**
 * Created by jacksondeng on 15/12/18.
 */

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void storeUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM User WHERE seed = :seed")
    User selectUsersBySeed(String seed);
    
    @Query("SELECT * FROM User")
    List<User> listAllUsers();
}
