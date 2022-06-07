package ru.netology.service


data class Chat(
    val id: Long,
    val messages: List<Message>,
    var readMessage: Boolean = false
)