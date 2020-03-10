package com.raphau.licho.di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raphau.licho.LichoApplication
import com.raphau.licho.experimental.ExperimentalActivity

open class InjectableActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        inject((application as LichoApplication).appComponent)
        super.onCreate(savedInstanceState)
    }

    private fun inject(appComponent: AppComponent) {
        when (this) {
            is ExperimentalActivity -> appComponent.inject(this)
            else -> throw AssertionError("This activity instance is non-injectable")
        }
    }
}