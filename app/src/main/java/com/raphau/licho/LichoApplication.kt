package com.raphau.licho

import android.app.Application
import com.raphau.licho.di.DaggerAppComponent

class LichoApplication: Application() {
    val appComponent = DaggerAppComponent.factory().create(this)
}