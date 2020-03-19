package com.raphau.licho.data

data class MessageThread(
    val id: Int,
    val address: String,
    val lastMessageDate: Long
) {
    var contact: Contact? = null
    val messages: List<Message> = ArrayList()

    fun add(message: Message) {
        (messages as ArrayList).add(message)
    }
}