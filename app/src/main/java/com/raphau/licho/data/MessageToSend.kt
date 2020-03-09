package com.raphau.licho.data

sealed class MessageToSend {
    abstract val destinationAddress: String
    abstract val scAddress: String?
}

data class SmsMessageToSend(
    override val destinationAddress: String,
    override val scAddress: String?,
    val text: String
) : MessageToSend()