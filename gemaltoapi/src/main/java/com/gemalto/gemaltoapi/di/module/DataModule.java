package com.gemalto.gemaltoapi.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.gemalto.gemaltoapi.api.GemaltoApi;
import com.gemalto.gemaltoapi.api.GemaltoApiInterface;
import com.gemalto.gemaltoapi.data.DataEncryption;
import com.gemalto.gemaltoapi.db.UserDb;
import com.gemalto.gemaltoapi.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jacksondeng on 15/12/18.
 */

@Module
public class DataModule {
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Application application){
        return application.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public UserDb provideUserDb(Application application){
        return Room.databaseBuilder(application,
                UserDb.class, "user_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository(){
        return new UserRepository();
    }


    @Provides
    @Singleton
    public DataEncryption dataEncryption(){
        return new DataEncryption();
    }
    @Provides
    @Singleton
    public GemaltoApi provideGemaltoApi(GemaltoApiInterface gemaltoApiInterface, UserDb userDb,
                                        UserRepository userRepository, DataEncryption dataEncryption){
        return new GemaltoApi(gemaltoApiInterface,userDb,userRepository,dataEncryption);
    }
}
