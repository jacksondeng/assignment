package com.gemalto.gemaltoapi;

import android.app.Activity;
import android.app.Application;
import android.support.compat.BuildConfig;

import com.gemalto.gemaltoapi.api.GemaltoApi;
import com.gemalto.gemaltoapi.di.component.DaggerAssignmentAppComponent;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by jacksondeng on 14/12/18.
 */

public class AssignmentApp extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    public GemaltoApi gemaltoApi;

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

    public GemaltoApi GegetGemaltoApi(){
        return gemaltoApi;
    }

}
