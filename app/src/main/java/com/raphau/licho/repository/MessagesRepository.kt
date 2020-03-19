package com.raphau.licho.repository

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.provider.Telephony
import android.telephony.SmsManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raphau.licho.data.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagesRepository @Inject constructor(private val context: Context,
                                             private val contactsRepository: ContactsRepository) {
    private val SMS_URI = Uri.parse("content://sms/")

    private val smsManager = SmsManager.getDefault()
    private val threadsLD = MutableLiveData<List<MessageThread>>()

    private val smsObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            GlobalScope.async {
                refreshMessages()
            }
        }
    }

    init {
        GlobalScope.async {
            refreshMessages()
        }
    }

     suspend fun start() {
        fetchMessages()
        context.contentResolver.registerContentObserver(SMS_URI, true, smsObserver)
    }

    fun stop() {
        context.contentResolver.unregisterContentObserver(smsObserver)
    }

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

    fun getMessages(): LiveData<List<MessageThread>> {
        return threadsLD
    }

    private suspend fun refreshMessages() {
        val messages = fetchMessages()
        threadsLD.postValue(messages)
    }

    private fun fetchMessages(): ArrayList<MessageThread> {
        val cursor = context.contentResolver.query(SMS_URI, null, null, null, "date DESC, date_sent DESC")
        val threadsMap = extractMessagesFromCursor(cursor)
        cursor?.close()
        return ArrayList<MessageThread>().apply {
            addAll(threadsMap.values)
        }
    }

    private fun extractMessagesFromCursor(cursor: Cursor?): LinkedHashMap<Int, MessageThread> {
        val threadsMap = LinkedHashMap<Int, MessageThread>()
        var contact: Contact? = null
        if (cursor != null && cursor.moveToFirst()) {
            var thread: MessageThread?
            var threadId: Int?
            var contactId: Int?
            var message: Message
            var type: Message.Type
            do {
                threadId = cursor.getInt(cursor.getColumnIndex("thread_id"))
                thread = threadsMap[threadId]
                type = getMessageType(cursor)
                contactId = cursor.getInt(cursor.getColumnIndex("person"))
                 if (contactId != contact?.id) {
                     contact = contactsRepository.getContact(contactId)
                }
                message = SmsMessage(
                    cursor.getString(cursor.getColumnIndex("address")),
                    cursor.getString(cursor.getColumnIndex("body")),
                    cursor.getLong(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("subject")),
                    cursor.getInt(cursor.getColumnIndex("seen")) != 0,
                    type,
                    contact,
                    cursor.getString(cursor.getColumnIndex("service_center"))
                )
                if (thread == null) {
                    thread = MessageThread(threadId, message.destinationAddress, message.date)
                    threadsMap[threadId] = thread
                }
                if (thread.contact == null) {
                    thread.contact = contact
                }
                thread.add(message)
            } while (cursor.moveToNext())
        }
        return threadsMap
    }

    private fun getMessageType(cursor: Cursor): Message.Type {
        return when (cursor.getInt(cursor.getColumnIndex("type"))) {
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_DRAFT -> Message.Type.DRAFT
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_FAILED -> Message.Type.FAILED
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_INBOX -> Message.Type.INBOX
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_OUTBOX -> Message.Type.OUTBOX
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_QUEUED -> Message.Type.QUEUED
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_SENT -> Message.Type.SENT
            else -> Message.Type.ALL
        }
    }
}