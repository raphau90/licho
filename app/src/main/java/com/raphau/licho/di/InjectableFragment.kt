package com.raphau.licho.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.raphau.licho.LichoApplication
import com.raphau.licho.experimental.ExperimentalFragment

open class InjectableFragment: Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject((activity?.application as LichoApplication).appComponent)
    }

    private fun inject(appComponent: AppComponent) {
        when (this) {
            is ExperimentalFragment -> appComponent.inject(this)
            else -> throw AssertionError("This fragment instance is non-injectable")
        }
    }
}