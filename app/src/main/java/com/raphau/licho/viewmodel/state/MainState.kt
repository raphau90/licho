package com.raphau.licho.viewmodel.state

sealed class MainState {

    data class MessagesList(var filter: String ="") : MainState()

    data class ShowThread(val threadId: Int = -1) : MainState()
}