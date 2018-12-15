package com.gemalto.assignment.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jacksondeng on 15/12/18.
 */

@Module
public class DataModule {
    @Provides
    public UserDb provideUserDb(Application application){
        return Room.databaseBuilder(application,
                UserDb.class, "user_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public UserRepository provideUserRepository(){
        return new UserRepository();
    }
}
