package com.raphau.licho.viewmodel.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raphau.licho.viewmodel.state.MainState
import java.util.*
import javax.inject.Inject

class MainInteractor @Inject constructor() : MainAction, ThreadsListAction {

    private val stateStack = Stack<MainState>().apply {
        push(MainState.ThreadsList())
    }
    private val stateLD = MutableLiveData<MainState?>().apply {
        postValue(stateStack.peek())
    }

    override fun back() {
        stateStack.pop()
        val state = if (!stateStack.empty()) {
            stateStack.peek()
        } else {
            null
        }
        stateLD.postValue(state)
    }

    override fun getState(): LiveData<MainState?> {
        return stateLD
    }

    override fun startNewThread() {
        stateStack.push(MainState.ShowThread())
    }

    override fun showThread(id: Int) {
        stateStack.push(MainState.ShowThread(id))
    }
}