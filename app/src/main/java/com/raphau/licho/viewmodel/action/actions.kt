package com.raphau.licho.viewmodel.action

import androidx.lifecycle.LiveData
import com.raphau.licho.viewmodel.state.MainState

interface Action {
    fun getState(): LiveData<MainState?>
}

interface MainAction : Action {
    fun back()
}

interface MessagesListAction : Action {
    fun showThread(id: Int)
    fun startNewThread()
}