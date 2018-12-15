package com.gemalto.assignment.repository;

import android.arch.lifecycle.MutableLiveData;

import com.gemalto.assignment.data.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jacksondeng on 15/12/18.
 */

@Singleton
public class UserRepository  {
    private MutableLiveData<List<User>> queriedUsers = new MutableLiveData<>();

    public MutableLiveData<List<User>> getQueriedUsers() {
        return queriedUsers;
    }

    public void setQueriedUsers(MutableLiveData<List<User>> queriedUsers) {
        this.queriedUsers = queriedUsers;
    }
}
