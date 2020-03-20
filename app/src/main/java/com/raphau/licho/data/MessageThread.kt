package com.raphau.licho.data

data class MessageThread(
    val id: Int,
    val address: String,
    val lastMessageDate: Long
) {
    var contact: Contact? = null
    set(value) {
        field = value
        displayName = value?.displayName ?: address
    }
    val messages: List<Message> = ArrayList()
    var isRead = true
    var displayName = address

    fun add(message: Message) {
        (messages as ArrayList).add(message)
    }
}