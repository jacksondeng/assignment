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
    private MutableLiveData<List<User>> remoteUsers = new MutableLiveData<>();
    private MutableLiveData<List<User>> localUsers = new MutableLiveData<>();

    public MutableLiveData<List<User>> getRemoteUsers() {
        return remoteUsers;
    }

    public void setRemoteUsers(MutableLiveData<List<User>> queriedUsers) {
        this.remoteUsers = queriedUsers;
    }

    public MutableLiveData<List<User>> getLocalUsers() {
        return localUsers;
    }

    public void setLocalUsers(MutableLiveData<List<User>> localUsers) {
        this.localUsers = localUsers;
    }
}
