package ru.netology.service

data class Message(
    val id: Long,
    val text: String,
    val authorId: Long,
)