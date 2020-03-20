package com.raphau.licho.data

abstract class Message {
    abstract val destinationAddress: String
    abstract val type: Type
    abstract val contact: Contact?
    abstract val scAddress: String?

    enum class Type {
        ALL, DRAFT, FAILED, INBOX, OUTBOX, QUEUED, SENT
    }

    fun isIncoming() = type == Type.INBOX
}

data class SmsMessage(
    override val destinationAddress: String,
    val text: String,
    val date: Long,
    val subject: String?,
    val isRead: Boolean = false,
    override val type: Type,
    override val contact: Contact?,
    override val scAddress: String? = null
) : Message()