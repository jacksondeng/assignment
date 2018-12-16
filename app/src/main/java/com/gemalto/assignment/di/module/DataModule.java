package com.gemalto.assignment.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.gemalto.assignment.GemaltoApi;
import com.gemalto.assignment.api.GemaltoApiInterface;
import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.repository.UserRepository;

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
    public GemaltoApi provideGemaltoApi(Application application,GemaltoApiInterface gemaltoApiInterface,
                                        UserDb userDb,UserRepository userRepository){
        return new GemaltoApi(application,gemaltoApiInterface,userDb,userRepository);
    }
}
