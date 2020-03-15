package com.raphau.licho.di

import com.raphau.licho.MainActivity
import com.raphau.licho.experimental.ExperimentalActivity
import com.raphau.licho.experimental.ExperimentalFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeExperimentalActivity(): ExperimentalActivity

    @ContributesAndroidInjector
    abstract fun contributeExperimentalFragment(): ExperimentalFragment
}