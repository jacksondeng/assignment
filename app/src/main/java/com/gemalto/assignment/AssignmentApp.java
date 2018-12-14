package com.gemalto.assignment;

import android.app.Activity;
import android.app.Application;

import com.gemalto.assignment.di.component.DaggerAssignmentAppComponent;

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

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            // Timber
            Timber.plant(new Timber.DebugTree());
        }

        DaggerAssignmentAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }
}
