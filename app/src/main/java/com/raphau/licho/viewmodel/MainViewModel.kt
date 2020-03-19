package com.raphau.licho.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphau.licho.repository.MessagesRepository
import com.raphau.licho.viewmodel.action.MainAction
import kotlinx.coroutines.launch

class MainViewModel(private val action: MainAction,
                    private val messagesRepository: MessagesRepository
) : ViewModel() {

    fun getState() = action.getState()

    fun onStart() {
        viewModelScope.launch {
            messagesRepository.start()
        }
    }

    fun onStop() = messagesRepository.stop()

}