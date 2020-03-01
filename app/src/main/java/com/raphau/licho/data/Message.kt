package com.raphau.licho.data

abstract class Message {
    abstract val destinationAddress: String
    abstract val scAddress: String
}

data class SmsMessage(
    override val destinationAddress: String,
    override val scAddress: String,
    val text: String
    ) : Message()