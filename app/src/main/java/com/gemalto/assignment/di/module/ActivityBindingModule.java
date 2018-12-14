package com.gemalto.assignment.di.module;

import com.gemalto.assignment.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jacksondeng on 15/12/18.
 */


@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeToMainActivity();
}