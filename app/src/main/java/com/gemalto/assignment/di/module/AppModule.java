package com.gemalto.assignment.di.module;

import android.content.Context;

import com.gemalto.assignment.AssignmentApp;

import dagger.Binds;
import dagger.Module;

/**
 * Created by jacksondeng on 14/12/18.
 */


@Module(includes = {ViewModelModule.class})
abstract class AppModule {
    @Binds
    abstract Context bindContext(AssignmentApp app);
}
