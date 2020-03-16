package com.raphau.licho.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.raphau.licho.MessagesRepository
import com.raphau.licho.data.MessageThread
import com.raphau.licho.viewmodel.action.MessagesListAction
import javax.inject.Inject

class MessagesListViewModel @Inject constructor(private val action: MessagesListAction,
                                                private val messagesRepository: MessagesRepository) : ViewModel() {

    private val filteredMessagesLD = MediatorLiveData<List<MessageThread>>()

    private val allMessagesLD = MediatorLiveData<List<MessageThread>>()

    var filter: String = ""

    init {
        allMessagesLD.addSource(messagesRepository.getMessages()) {
            allMessagesLD.postValue(it)
        }
        filteredMessagesLD.addSource(allMessagesLD) {
            filteredMessagesLD.postValue(filterMessages())
        }
        allMessagesLD.postValue(messagesRepository.getMessages().value)
    }

    private fun filterMessages(): List<MessageThread> {
        //TODO: filter messages
        return allMessagesLD.value ?: ArrayList()
    }

    fun getMessages(): LiveData<List<MessageThread>> = filteredMessagesLD
}

