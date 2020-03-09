package com.raphau.licho

import android.content.Context
import android.telephony.SmsManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raphau.licho.data.*

class MessagesRepository(val context: Context) {
    private val SMS_URI = Uri.parse("content://sms/inbox")

    private val smsManager = SmsManager.getDefault()

    fun sendMessage(message: MessageToSend) {
        when (message) {
            is SmsMessageToSend -> smsManager.sendTextMessage(
                message.destinationAddress,
                message.scAddress,
                message.text,
                null,
                null)
        }
    }
}