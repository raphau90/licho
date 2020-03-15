package com.raphau.licho.di

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

abstract class InjectableFragment: Fragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        onInjected()
    }

    abstract fun onInjected()
}