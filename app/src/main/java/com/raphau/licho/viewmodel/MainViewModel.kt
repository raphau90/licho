package com.raphau.licho.viewmodel

import androidx.lifecycle.ViewModel
import com.raphau.licho.MessagesRepository
import com.raphau.licho.viewmodel.action.MainAction

class MainViewModel(private val action: MainAction,
                    private val messagesRepository: MessagesRepository) : ViewModel() {

    fun getState() = action.getState()

    fun onStart() = messagesRepository.start()

    fun onStop() = messagesRepository.start()

}