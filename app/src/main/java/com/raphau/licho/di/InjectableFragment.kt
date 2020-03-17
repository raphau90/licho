package com.raphau.licho.di

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class InjectableFragment: Fragment() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInjected()
    }

    abstract fun onInjected()
}