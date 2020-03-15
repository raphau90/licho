package com.raphau.licho.viewmodel

import androidx.lifecycle.ViewModel
import com.raphau.licho.MessagesRepository
import com.raphau.licho.viewmodel.action.MessagesListAction
import javax.inject.Inject

class MessagesListViewModel @Inject constructor(private val action: MessagesListAction,
                                                private val messagesRepository: MessagesRepository) : ViewModel()