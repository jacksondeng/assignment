package com.gemalto.assignment;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import com.commonsware.cwac.saferoom.SQLCipherUtils;
import com.gemalto.assignment.api.ApiResponse;
import com.gemalto.assignment.api.GemaltoApiInterface;
import com.gemalto.assignment.data.User;
import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by jacksondeng on 16/12/18.
 */

@Singleton
public class GemaltoApi {
    private HashMap<String, String> queryParams = new HashMap<>();
    private GemaltoApiInterface gemaltoApiInterface;
    private UserDb userDb;
    private Context context;
    private UserRepository userRepository;
    private SingleLiveEvent<Boolean> apiSuccess = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> showNetworkError = new SingleLiveEvent();

    @Inject
    public GemaltoApi(Application application, GemaltoApiInterface gemaltoApiInterface,
                      UserDb userDb, UserRepository userRepository) {
        this.gemaltoApiInterface = gemaltoApiInterface;
        this.userDb = userDb;
        this.context = application;
        this.userRepository = userRepository;
    }

    void getUsers(String gender, String seed, String number) {
        buildMap(gender, seed, number);
        gemaltoApiInterface.getUsers(queryParams).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        apiSuccess.postValue(false);
                        showNetworkError.postValue(false);
                    }

                    @Override
                    public void onNext(ApiResponse apiResponse) {
                        for(User user : apiResponse.getUser()){
                            user.setSeed(apiResponse.getInfo().getSeed());
                        }
                        userRepository.getRemoteUsers().postValue(apiResponse.getUser());
                        apiSuccess.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetworkError.postValue(true);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    private void buildMap(String gender, String seed, String  number) {
        queryParams.clear();
        queryParams.put("gender", gender.toLowerCase());
        if (seed.length() > 0) {
            queryParams.put("seed", seed);
        }
        if(number.length()>0) {
            queryParams.put("results", String.valueOf(number));
        }
    }

    void listStoredUsers(){
        if(isDbEncrypted()){
            decryptDb();
        }
        new Thread(() -> {
            List<User> users;
            users = userDb.userDao().listAllUsers();
            Observable.fromIterable(users)
                    .map(user -> {
                        user.setIsStored(1);
                        return user;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                    });
            userRepository.getLocalUsers().postValue(users);
            encryptDb();
        }) .start();
    }

    void storeUser(final User user){
        if(isDbEncrypted()){
            decryptDb();
        }

        new Thread(() -> {
            userDb.userDao().storeUser(user);
            listStoredUsers();
        }).start();
    }

    void deleteUser(final User user){
        if(isDbEncrypted()){
            decryptDb();
        }

        new Thread(() -> {
            userDb.userDao().deleteUser(user);
            listStoredUsers();
        }).start();
    }


    private boolean isDbEncrypted() {
        return (SQLCipherUtils.getDatabaseState(context, userDb.getOpenHelper().getDatabaseName())
                == SQLCipherUtils.State.ENCRYPTED);
    }

    public boolean checkDataBase() {
        String filePath = context.getApplicationInfo().dataDir+"/databases/user_db";
        File databaseFile = new File(filePath);
        return databaseFile.exists();
    }

    private void encryptDb() {
        try {
            SQLCipherUtils.encrypt(context, userDb.getOpenHelper().getDatabaseName()
                    , "gemaltoassignment2018".toCharArray());
        } catch (IOException e) {
            Timber.d("DatabaseState  " + e.toString());
        }
    }

    private void decryptDb(){
        String filePath = context.getApplicationInfo().dataDir+"/databases/user_db";
        File databaseFile = new File(filePath);
        try {
            SQLCipherUtils.decrypt(context, databaseFile
                    , "gemaltoassignment2018".toCharArray());
        } catch (IOException e) {
            Timber.d("DatabaseState  " + e.toString());
        }
    }

    UserRepository getUserRepository(){
        return userRepository;
    }

    public MutableLiveData<Boolean> getApiSuccess(){
        return apiSuccess;
    }


    public MutableLiveData<Boolean> getShowNetworkError(){
        return showNetworkError;
    }
}
