package com.raphau.licho

import android.content.Context
import android.telephony.SmsManager
import com.raphau.licho.data.Message
import com.raphau.licho.data.SmsMessage

class MessagesRepository(context: Context) {
    private val smsManager = SmsManager.getDefault()

    fun sendMessage(message: Message) {
        when (message) {
            is SmsMessage -> smsManager.sendTextMessage(
                message.destinationAddress,
                message.scAddress,
                message.text,
                null,
                null)
        }
    }
}