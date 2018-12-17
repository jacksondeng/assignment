package com.gemalto.assignment.api;

import android.arch.lifecycle.MutableLiveData;
import com.gemalto.assignment.data.DataEncryption;
import com.gemalto.assignment.data.User;
import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.repository.UserRepository;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jacksondeng on 16/12/18.
 */

@Singleton
public class GemaltoApi {
    private HashMap<String, String> queryParams = new HashMap<>();
    private GemaltoApiInterface gemaltoApiInterface;
    private UserDb userDb;
    private UserRepository userRepository;
    private DataEncryption dataEncryption;
    private MutableLiveData<Boolean> apiSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> showNetworkError = new MutableLiveData<>();

    @Inject
    public GemaltoApi(GemaltoApiInterface gemaltoApiInterface, UserDb userDb,
                      UserRepository userRepository,DataEncryption dataEncryption) {
        this.gemaltoApiInterface = gemaltoApiInterface;
        this.userDb = userDb;
        this.userRepository = userRepository;
        this.dataEncryption = dataEncryption;
    }

    public void getUsers(String gender, String seed, String number) {
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

    public void listStoredUsers(){
        new Thread(() -> {
            List<User> users;
            users = userDb.userDao().listAllUsers();
            Observable.fromIterable(users)
                    .map(user -> {
                        user.setIsStored(1);
                        dataEncryption.decryptUser(user);
                        return user;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                    });
            userRepository.getLocalUsers().postValue(users);
        }) .start();
    }

    public void storeUser(final User user){
        new Thread(() -> {
            dataEncryption.encryptUser(user);
            userDb.userDao().storeUser(user);
            listStoredUsers();
        }).start();
    }

    public void deleteUser(final User user){
        new Thread(() -> {
            dataEncryption.encryptUser(user);
            userDb.userDao().deleteUser(user);
            listStoredUsers();
        }).start();
    }




    public UserRepository getUserRepository(){
        return userRepository;
    }

    public MutableLiveData<Boolean> getApiSuccess(){
        return apiSuccess;
    }


    public MutableLiveData<Boolean> getShowNetworkError(){
        return showNetworkError;
    }
}
