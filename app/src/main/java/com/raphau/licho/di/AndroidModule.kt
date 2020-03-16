package com.raphau.licho.di

import com.raphau.licho.MainActivity
import com.raphau.licho.MessageThreadFragment
import com.raphau.licho.MessagesListFragment
import com.raphau.licho.experimental.ExperimentalActivity
import com.raphau.licho.experimental.ExperimentalFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMessagesListFragment(): MessagesListFragment

    @ContributesAndroidInjector
    abstract fun contributeMessageThreadFragment(): MessageThreadFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeExperimentalActivity(): ExperimentalActivity

    @ContributesAndroidInjector
    abstract fun contributeExperimentalFragment(): ExperimentalFragment
}