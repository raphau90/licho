package com.raphau.licho.data

data class MessageThread(
    val id: Int,
    val address: String,
    val lastMessageDate: Long
) {
    var personId = 0
    val messages: List<Message> = ArrayList()

    fun add(message: Message) {
        (messages as ArrayList).add(message)
    }
}