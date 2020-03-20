package com.raphau.licho.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.raphau.licho.repository.MessagesRepository
import com.raphau.licho.data.MessageThread
import com.raphau.licho.viewmodel.action.ThreadsListAction
import javax.inject.Inject

class ThreadsListViewModel @Inject constructor(private val action: ThreadsListAction,
                                               private val messagesRepository: MessagesRepository
) : ViewModel() {

    private val filteredMessagesLD = MediatorLiveData<List<MessageThread>>()

    private val allMessagesLD = MediatorLiveData<List<MessageThread>>()

    var filter: String = ""
        set (value) {
            field = value
            filteredMessagesLD.postValue(filterMessages())
        }

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
        val allMessages = allMessagesLD.value ?: ArrayList()
        return allMessages.filter {
            it.displayName.contains(filter, true)
        }
    }

    fun getMessages(): LiveData<List<MessageThread>> = filteredMessagesLD
}

