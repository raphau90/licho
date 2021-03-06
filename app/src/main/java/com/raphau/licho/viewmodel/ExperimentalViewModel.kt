package com.raphau.licho.viewmodel

import androidx.lifecycle.ViewModel
import com.raphau.licho.viewmodel.action.MainAction

class ExperimentalViewModel(private val action: MainAction) : ViewModel() {

    fun getState() = action.getState()

}