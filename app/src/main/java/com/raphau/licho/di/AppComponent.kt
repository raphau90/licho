package com.raphau.licho.di

import android.content.Context
import com.raphau.licho.experimental.ExperimentalActivity
import com.raphau.licho.experimental.ExperimentalFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: ExperimentalActivity)

    fun inject(fragment: ExperimentalFragment)
}