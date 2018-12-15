package com.gemalto.assignment;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;

import com.gemalto.assignment.db.UserDb;
import com.gemalto.assignment.di.component.DaggerAssignmentAppComponent;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by jacksondeng on 14/12/18.
 */

public class AssignmentApp extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    private UserDb userDb;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        // Init JodaTime
        JodaTimeAndroid.init(this);

        initDagger();
    }

    public void initDagger(){
        // Init dagger
        DaggerAssignmentAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }


    public UserDb getUserDb() {
        return userDb;
    }
}
