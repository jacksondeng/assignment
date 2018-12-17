package com.gemalto.gemaltoapi.di.component;

import android.app.Application;

import com.gemalto.gemaltoapi.di.module.ActivityBindingModule;
import com.gemalto.gemaltoapi.AssignmentApp;
import com.gemalto.gemaltoapi.di.module.DataModule;
import com.gemalto.gemaltoapi.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by jacksondeng on 14/12/18.
 */


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class,
        ActivityBindingModule.class,NetworkModule.class, DataModule.class} )

public interface AssignmentAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AssignmentAppComponent build();
    }

    void inject(AssignmentApp app);
}
